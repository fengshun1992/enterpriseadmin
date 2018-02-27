package com.admin.controller.enterprise.publishController;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.*;
import com.admin.entity.system.User;
import com.admin.service.enterprise.contract.ContractService;
import com.admin.service.enterprise.contractFilesService.ContractFilesService;
import com.admin.service.enterprise.contractTemplate.ContractTemplateService;
import com.admin.service.enterprise.fileParams.FileParamsService;
import com.admin.service.enterprise.fileParamsValues.FileParamsValuesService;
import com.admin.service.enterprise.modelFiles.ModelFilesService;
import com.admin.service.enterprise.oprationRecord.OprationRecordService;
import com.admin.service.enterprise.orgMenbers.OrgMenbersService;
import com.admin.service.enterprise.publishContract.PublishContractService;
import com.admin.service.system.user.UserService;
import com.admin.util.*;
import com.admin.vo.FileParamValueVO;
import com.admin.vo.FileParamsValuesVO;
import com.admin.vo.TableCellVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.admin.util.PdfStampUtils.setWatermark;

/**
 * 该Controller已废弃
 */
@Controller
@Scope("session")
@RequestMapping(value = "/publishContract")
public class PublishContractController extends BaseController{
    
    String menuUrl = "orgnation/list.do"; // 菜单地址(权限用)

    @Resource(name = "contractTemplateService")
    private ContractTemplateService contractTemplateService;

    @Resource(name = "publishContractService")
    private PublishContractService publishContractService;

    @Resource(name = "fileParamsService")
    private FileParamsService fileParamsService;

    @Resource(name = "fileParamsValuesService")
    private FileParamsValuesService fileParamsValuesService;

    @Resource(name = "contractService")
    private ContractService contractService;

    @Resource(name = "modelFilesService")
    private ModelFilesService modelFilesService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "contractFilesService")
    private ContractFilesService contractFilesService;

    @Resource(name = "orgMenbersService")
    private OrgMenbersService orgMenbersService;

    @Resource(name = "oprationRecordService")
    private OprationRecordService oprationRecordService;

    private String Path = "/contract/";

    private Integer contractId;

    private Integer templateId;

    //用于存储modelFileId和targetFileId的关系
    private Map<Integer,Integer> mtMap = new HashMap<>();





    /**
     * 去发行合同界面
     */
    @RequestMapping(value = "/goPubContract")
    public ModelAndView goPubContractPage(Page page, HttpServletRequest request) throws Exception {
        logBefore(logger, "去发行合同界面");
        ModelAndView mv = this.getModelAndView();
        //获取当前登录用户id
        Long userId = getUser().getUSER_ID();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("userId",userId);
        page.setPd(pd);

        OrgMenbers orgMenbers = orgMenbersService.findByUserId(pd);
        pd.put("publisherOrgId",orgMenbers.getOrgId());
        List<PageData> contractTemplateList = contractTemplateService.listPubTemplate(page);
        mv.setViewName("enterprise/contract/publishContract");
        mv.addObject("contractTemplateList", contractTemplateList);
        mv.addObject("msg", "saveContract");
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 获取模板参数
     */
    @RequestMapping(value = "/getTemplateParam")
    public ModelAndView getTemplateParam(Integer templateId) throws Exception {
        logBefore(logger, "获取模板参数");
        ModelAndView mv = this.getModelAndView();

        //根据templateId查询出对应的modelfiles
        List<ModelFiles> modelFilesList = modelFilesService.listByTemplateId(templateId);
        List<FileParams> fileParamsList = new ArrayList<>();

        Map<ModelFiles,List<FileParams>> fileParamsMap = new HashMap<>();


        //查询出文件对应的参数
        for (ModelFiles mf : modelFilesList){
            //根据modelFileId查询出所有的参数
            fileParamsList = fileParamsService.listParam(mf.getModelFileId());

            fileParamsMap.put(mf,fileParamsList);

        }

        if(templateId != null){
            ContractTemplate template = contractTemplateService.findByTemplateId(templateId);

            mv.addObject("template", template);
            mv.addObject("modelFilesList", modelFilesList);
            mv.addObject("fileParamsMap", fileParamsMap);

        }
        mv.setViewName("enterprise/contract/templateParam");
        return mv;
    }

    /**
     * 合同的生成
     */
    @RequestMapping(value = "/saveContract")
    public ModelAndView saveContract(Integer templateId,String contractName,String contractNo, @RequestParam(value = "FILE", required = false) MultipartFile[] FILE) throws Exception {
        logBefore(logger, "新增contract");
        ModelAndView mv = this.getModelAndView();
        ContractTemplate template = contractTemplateService.findByTemplateId(templateId);
        this.templateId = templateId;
        Contract contract = new Contract();
        contract.setContractName(contractName);
        contract.setContractNo(contractNo);
        contract.setTemplateId(templateId);
        contract.setPublisherOrgId(template.getPublisherOrgId());
        contract.setDelegatorOrgId(template.getDelegatorOrgId());
        contract.setStatus(1);//1-待受托管理人审核 2-待风控审核 3-驳回 4-已归档
        //获取合同管理机构id
        Long creatorId = template.getCreatorId();
        PageData pd = new PageData();
        pd.put("userId", creatorId);
        OrgMenbers orgMenbers = orgMenbersService.findByUserId(pd);
        pd.put("orgId", orgMenbers.getOrgId());
        //获取合同管理机构id
        contract.setManageOrgId((Integer) pd.get("orgId"));
        contract.setCreateTime(DateUtil.getTime());

        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            contractService.save(contract);
            //记录操作日志
            OprationRecord oprationRecord = new OprationRecord();
            oprationRecord.setContractId(contract.getContractId());
            oprationRecord.setOpType(1);//发行
            oprationRecord.setOpId(getUser().getUSER_ID());
            oprationRecord.setOpTime(DateUtil.getTime());
            oprationRecordService.save(oprationRecord);
        } // 判断新增权限

        contractId = contract.getContractId();
        //根据templateId查询出modelFiles
        List<ModelFiles> modelFiles = modelFilesService.listByTemplateId(templateId);
        for (ModelFiles mf : modelFiles){
            ContractFiles contractFiles = new ContractFiles();
            contractFiles.setContractId(contractId);
            contractFiles.setModelFileId(mf.getModelFileId());
            contractFiles.setFileName(mf.getFileName());
            contractFiles.setUrl(SysConstant.LOCAL_FILE + "contract/" + contract.getContractId() + "/temp/word/" + contractFiles.getFileName());
            contractFilesService.saveOne(contractFiles);
            Integer modelFileId = mf.getModelFileId();
            Integer targetFileId = contractFiles.getTargetFileId();
            mtMap.put(modelFileId,targetFileId);

        }
        mv.addObject("msg", "success");
        mv.setViewName("enterprise/contract/fjUpload");
        return mv;
    }


    /**
     * 保存用户输入的参数值,并生成合同文件
     */
    @RequestMapping(value = "/saveFileParamValue")
    public ModelAndView saveFileParamValue(@RequestBody FileParamValueVO vo) throws Exception {
        logBefore(logger, "保存参数值,并生成合同文件");
        ModelAndView mv = this.getModelAndView();

        //用于存储FileParamsValues
        List<FileParamsValues> fpvList = new ArrayList<>();

        //用于存储targetFileId
        List<Integer> targetFileIdList = new ArrayList<>();

        List<FileParamsValuesVO> paramValueList = vo.getParamValueList();
        if(paramValueList != null && paramValueList.size() > 0 ){
            for(FileParamsValuesVO fpv : paramValueList){
                FileParamsValues fileParamsValues = new FileParamsValues();
                fileParamsValues.setParamId(fpv.getParamId());
                fileParamsValues.setParam(fpv.getParam());
                fileParamsValues.setValue(fpv.getValue());
                fileParamsValues.setTargetFileId(mtMap.get(fpv.getModelFileId()));
                fpvList.add(fileParamsValues);

                //存储targetFIleId
                Integer targetFileId = mtMap.get(fpv.getModelFileId());
                if(!targetFileIdList.contains(targetFileId)){
                    targetFileIdList.add(targetFileId);
                }

            }
        }

        List<TableCellVO> tableParamList = vo.getTableParamList();
        if(tableParamList != null && tableParamList.size() > 0){
            for(TableCellVO tc : tableParamList){
                FileParamsValues fileParamsValues = new FileParamsValues();

                //保存参数值
                fileParamsValues.setParam(tc.getParam());
                fileParamsValues.setValue(tc.getValue());
                fileParamsValues.setRowIndex(tc.getRowIndex());
                fileParamsValues.setColumnIndex(tc.getColumnIndex());
                fileParamsValues.setTargetFileId(mtMap.get(tc.getModelFileId()));
                fileParamsValues.setTableName(tc.getTableName());
                fileParamsValues.setParamId(tc.getParamId());
                fpvList.add(fileParamsValues);
            }
        }

        //保存参数值
        fileParamsValuesService.save(fpvList);

        //根据目标文件id依次生成临时合同
        for (Integer targetFileId : targetFileIdList){
            List<WordTable> tableList = new ArrayList<WordTable>();
            Map<String,String> map = new HashMap<>();
            List<FileParamsValues> paramList = fileParamsValuesService.findParamById(targetFileId);
            for (FileParamsValues fp : paramList){
                map.put(fp.getParam(),fp.getValue());
            }
            List<FileParamsValues> tableParamList1 = fileParamsValuesService.findTableParamById(targetFileId);
            List<TableCell> tableCells = new ArrayList<>();
            WordTable wordTable = new WordTable();
            if (tableParamList1 != null && tableParamList1.size() > 0){
                for (FileParamsValues fpv : tableParamList1){
                    wordTable.setTableName(fpv.getTableName());
                    wordTable.setRows(2);
                    TableCell tableCell = new TableCell();
                    tableCell.setParam(fpv.getParam());
                    tableCell.setValue(fpv.getValue());
                    tableCell.setRowIndex(fpv.getRowIndex());
                    tableCell.setColumnIndex(fpv.getColumnIndex());
                    tableCells.add(tableCell);
                }
                wordTable.setColumnList(tableCells);
                tableList.add(wordTable);
            }

            ContractFiles contractFiles = contractFilesService.findByTargetFileId(targetFileId);

            ModelFiles modelFile = modelFilesService.findByModelId(contractFiles.getModelFileId());
            String inputWordUrl = modelFile.getUrl();
            String outputWordUrl = contractFiles.getUrl();
            InputStream is = new FileInputStream(inputWordUrl);
            WordTemplateUtils.changWord(is, outputWordUrl, map, tableList);

            //将word文档转化为pdf
            String inputPdfUrl  = outputWordUrl;
            String pdfName = contractFiles.getFileName().replace("docx","pdf");
            String outputPdfUrl = outputWordUrl.replace("word/"+contractFiles.getFileName(),"pdf/first/"+pdfName);
            Word2PdfUtils.wordToPdf(inputPdfUrl,outputPdfUrl);

            //盖章
            String inputStampUrl = outputPdfUrl;
            String secondPdfName = pdfName.substring(0,pdfName.indexOf(".")) + "-2.pdf";
            String outputStampUrl = outputPdfUrl.replace("first/" +pdfName ,"second/" + secondPdfName);
            String imgUrl = "D:\\data\\qiye\\stamp\\yitong\\fr\\1-1.png";
            //File file = new File(imgUrl);
            List<ContractStamp> list = new ArrayList<>();
            ContractStamp contractStamp = new ContractStamp();
            contractStamp.setPageNo(9);
            contractStamp.setAbsoluteX(470);
            contractStamp.setAbsoluteY(270);
            contractStamp.setRotate(10);
            contractStamp.setImgUrl(imgUrl);
            list.add(contractStamp);
            // 将pdf文件先加水印然后输出
            setWatermark(inputStampUrl, list, outputStampUrl);


        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去保存合同附件
     */
    @RequestMapping(value = "/goSaveContractFJ")
    public ModelAndView goSaveContractFJ() throws Exception {
        logBefore(logger, "去保存合同附件界面");
        ModelAndView mv = this.getModelAndView();
        //文件预览
        List<ContractFiles> cfList = contractFilesService.findWordByContractId(contractId);
        mv.addObject("cfList", cfList);
        mv.addObject("msg","saveContractFJ");
        mv.setViewName("enterprise/contract/fjUpload");
        return mv;
    }



    /**
     * 保存合同附件
     */
    @RequestMapping(value = "/saveContractFJ")
    public ModelAndView saveContractFJ(@RequestParam(value = "FILE", required = false) MultipartFile[] FILE) throws Exception {
        logBefore(logger, "保存合同附件");
        ModelAndView mv = this.getModelAndView();
        //文件上传
        String originalImgname = "";
        String url = "";
        ArrayList<ContractFiles> contractFilesList = new ArrayList<>();
        for (MultipartFile item : FILE) {
            originalImgname = item.getOriginalFilename();//原始名称

            if (originalImgname != null && originalImgname.length() > 0) {
                logger.info("文件上传:" + originalImgname);
                try {
                    //上传sftp服务器
                    url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                            DateUtil.getDays(), originalImgname, Path + contractId + "/temp/fj/" );
                    //上传sftp
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    ContractFiles contractFiles = new ContractFiles();
                    contractFiles.setUrl(url);
                    contractFiles.setContractId(contractId);
                    contractFiles.setFileName(originalImgname);
                    contractFiles.setModelFileId(null);
                    contractFilesList.add(contractFiles);
                }
            }
        }

        if (contractFilesList.size() > 0) {
            contractFilesService.save(contractFilesList);
        }

        //设置合同模板状态为已发行
        /*Contract contract =  contractService.findByContractId(contractId);
        ContractTemplate contractTemplate = contractTemplateService.findByTemplateId(contract.getTemplateId());
        contractTemplate.setStatus(1);
        contractTemplateService.update(contractTemplate);*/
        mv.addObject("msg", "success");
        mv.setViewName("enterprise/contract/contract_list");
        return mv;
    }






    /* ===============================登录用户================================== */
    public User getUser() {
        Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
        Session session = currentUser.getSession();
        return (User) session.getAttribute(Const.SESSION_USERROL);
    }
    /* ===============================登录用户================================== */


    /* ===============================权限================================== */
    public Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */
}
