package com.admin.controller.enterprise.republishContract;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.*;
import com.admin.entity.system.User;
import com.admin.service.enterprise.contract.ContractService;
import com.admin.service.enterprise.contractFilesService.ContractFilesService;
import com.admin.service.enterprise.contractStamp.ContractStampService;
import com.admin.service.enterprise.contractTemplate.ContractTemplateService;
import com.admin.service.enterprise.fileParams.FileParamsService;
import com.admin.service.enterprise.fileParamsValues.FileParamsValuesService;
import com.admin.service.enterprise.modelFiles.ModelFilesService;
import com.admin.service.enterprise.oprationRecord.OprationRecordService;
import com.admin.service.enterprise.orgMenbers.OrgMenbersService;
import com.admin.service.enterprise.orgnation.OrgnationService;
import com.admin.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/rePublishContract")
public class RePublishContractController extends BaseController {

    String menuUrl = "rePublishContract/list.do"; // 菜单地址(权限用)
    @Resource(name = "contractService")
    private ContractService contractService;
    @Resource(name = "orgMenbersService")
    private OrgMenbersService orgMenbersService;
    @Resource(name = "orgnationService")
    private OrgnationService orgnationService;
    @Resource(name = "contractFilesService")
    private ContractFilesService contractFilesService;
    @Resource(name = "oprationRecordService")
    private OprationRecordService oprationRecordService;
    @Resource(name = "fileParamsValuesService")
    private FileParamsValuesService fileParamsValuesService;
    @Resource(name = "contractTemplateService")
    private ContractTemplateService contractTemplateService;
    @Resource(name = "modelFilesService")
    private ModelFilesService modelFilesService;
    @Resource(name = "fileParamsService")
    private FileParamsService fileParamsService;
    @Resource(name = "contractStampService")
    private ContractStampService contractStampService;

    String path = SysConstant.LOCAL_FILE;

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, "显示rePublishContract列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String startDate = pd.getString("lastLoginStart");
        String endDate = pd.getString("endDate");

        if (startDate != null && !"".equals(startDate)) {
            if (!startDate.contains(" 00:00:00")) {
                startDate = startDate + " 00:00:00";
            }
            pd.put("startDate", startDate);
        }
        if (endDate != null && !"".equals(endDate)) {
            if (!endDate.contains(" 00:00:00")) {
                endDate = endDate + " 00:00:00";
            }
            pd.put("endDate", endDate);
        }
        Long userId = getUser().getUSER_ID();
        pd.put("userId", userId);
        OrgMenbers orgMenbers = orgMenbersService.findByUserId(pd);
        if (orgMenbers == null) {
            mv.setViewName("enterprise/rePublishContract/rePublishContract_list");
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
            return mv;
        }
        pd.put("orgId", orgMenbers.getOrgId());
        pd.put("status", 3);//驳回
        page.setPd(pd);

        List<PageData> contractList = contractService.bhListPage(page);
        List<PageData> orgnationList = orgnationService.allList(pd);

        mv.setViewName("enterprise/rePublishContract/rePublishContract_list");
        mv.addObject("contractList", contractList);
        mv.addObject("orgnationList", orgnationList);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit(Integer contractId) throws Exception {
        logBefore(logger, "去修改rePublishContract页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Contract contract = contractService.findByContractId(contractId);

        List<ContractFiles> contractFilesList = contractFilesService.findWordByContractId(contractId);
        for (int i = 0; i < contractFilesList.size(); i++){
            String wordName = contractFilesList.get(i).getFileName();
            List<FileParamsValues> fileParamsValuesList = fileParamsValuesService.listByTargetFileId(contractFilesList.get(i).getTargetFileId());

            pd.put("word_" + (i + 1), wordName);
            pd.put("fileParamsValuesList_" + (i + 1), fileParamsValuesList);
        }

        mv.setViewName("enterprise/rePublishContract/rePublishContract_edit");
        mv.addObject("msg", "update");
        mv.addObject("templateId", contract.getTemplateId());
        mv.addObject("contract", contract);
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update")
    public ModelAndView save(Contract contract, HttpServletRequest request) throws Exception {
        logBefore(logger, "更新rePublishContract");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        List<ContractFiles> contractFilesList = new ArrayList<>();
        int templateId = Integer.parseInt(request.getParameter("templateId"));
        int contractId = contract.getContractId();

        List<FileParamsValues> fileParamsValuesList = new ArrayList<>();
        List<ModelFiles> modelFilesList = modelFilesService.listByTemplateId(templateId);
        for (int i = 0; i < modelFilesList.size(); i++) {
            //需要替换的参数
            Map textMap = new HashedMap();
            List<WordTable> tableList = new ArrayList<>();
            WordTable wordTable = new WordTable();
            List<TableCell> tableCells = new ArrayList<>();
            String tableName = "";

            ModelFiles modelFiles = modelFilesList.get(i);
            int modelFileId = modelFiles.getModelFileId();
            String modelFileName = modelFiles.getFileName();
            //模型文件关联的参数
            List<FileParams> fileParamsList = fileParamsService.listParam(modelFiles.getModelFileId());
            for (int j = 0; j < fileParamsList.size(); j++) {
                FileParamsValues fileParamsValues = new FileParamsValues();
                FileParams fileParams = fileParamsList.get(j);
                int paramId = fileParams.getParamId();
                //从表单获取值
                String value = request.getParameter("_" + paramId);
                fileParams.setValue(value);
                //文本替换
                if (fileParams.getParamType() == 1) {
                    textMap.put(fileParams.getParam(), value);
                } else {
                    tableName = fileParams.getTableName();
                    //表格替换
                    TableCell tableCell = new TableCell();
                    BeanUtils.copyProperties(fileParams, tableCell);
                    tableCell.setRowIndex(2);
                    tableCells.add(tableCell);
                    if ("票据质押清单.出票金额".equals(tableCell.getParam())) {
                        //---添加出票金额.合计---
                        TableCell cell = new TableCell();
                        cell.setParam("出票金额.合计");
                        cell.setValue(tableCell.getValue());
                        cell.setColumnIndex(tableCell.getColumnIndex());
                        cell.setRowIndex(6);
                        tableCells.add(cell);
                        //---end---
                    }
                }
                BeanUtils.copyProperties(fileParams, fileParamsValues);

                fileParamsValues.setRowIndex(2);
                fileParamsValuesList.add(fileParamsValues);
            }

            wordTable.setRows(4);
            wordTable.setTableName(tableName);
            wordTable.setColumnList(tableCells);
            tableList.add(wordTable);

            //填入word文档里
            String inputUrl = modelFiles.getUrl();
            String inputPath = path + inputUrl.split(SysConstant.FILE_HTTPURL + "/")[1];
            InputStream is = new FileInputStream(inputPath);
            String path2 = "contract/" + contractId + "/temp/word/";
            FileUtil.createDir(path + path2);
            String outputPath = path + path2 + modelFileName + ".docx";
            if(new File(outputPath).exists()){
                FileUtil.delFile(outputPath);
                WordTemplateUtils.changWord(is, outputPath, textMap, tableList);
            }
            String url = SysConstant.FILE_HTTPURL + "/" + path2 + modelFileName + ".docx";
            //重新保存合同文件
            pd.put("modelFileId",modelFileId);
            pd.put("contractId",contractId);
            ContractFiles cf = contractFilesService.findByContractIdAndModelFileId(pd);
            Integer oldTargetFileId = cf.getTargetFileId();
            contractFilesService.deleteByTargetFileId(cf.getTargetFileId());
            //根据targetFileId删除参数值
            fileParamsValuesService.deleteByTargetFileId(oldTargetFileId);
            ContractFiles contractFiles = new ContractFiles();
            contractFiles.setContractId(contractId);
            contractFiles.setModelFileId(modelFileId);
            contractFiles.setFileName(modelFileName + "(" + contract.getContractNo() + ")");
            contractFiles.setUrl(url);
            contractFilesList.add(contractFiles);
            if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
                contractFilesService.saveOne(contractFiles);
            }

            //给value表赋targetFileId值
            for (FileParamsValues fileParamsValues : fileParamsValuesList) {
                //遍历list，如果没有targetFileId则赋值
                if (fileParamsValues.getTargetFileId() == null) {
                    fileParamsValues.setTargetFileId(contractFiles.getTargetFileId());
                }
            }



        }

        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            //保存参数值到数据库
            fileParamsValuesService.save(fileParamsValuesList);
        }

        //更新合同
        updateContract(contract);
        pd.put("contractId", contractId);
        mv.setViewName("enterprise/rePublishContract/rePublishContract_edit2");
        mv.addObject("msg", "updateNext");
        mv.addObject("contractFilesList", contractFilesList);
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 更新合同
     *
     * @param contract
     * @throws Exception
     */
    private void updateContract(Contract contract) throws Exception {
        contract.setUpdateTime(DateUtil.getTime());
        if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            //更新合同
            contractService.update(contract);
        } // 判断新增权限
    }

    /**
     * 保存下一步
     */
    @RequestMapping(value = "/updateNext")
    public ModelAndView saveNext(HttpServletRequest request, @RequestParam(value = "FILE", required = false) MultipartFile[] FILE) throws Exception {
        logBefore(logger, "更新rePublishContract下一步");
        ModelAndView mv = this.getModelAndView();

        List<ContractFiles> fjList = new ArrayList<>();
        int contractId = Integer.parseInt(request.getParameter("contractId"));

        //先删除合同附件
        List<ContractFiles> contractFJList = contractFilesService.findContractFJ(contractId);
        for (ContractFiles fj :  contractFJList){
            contractFilesService.deleteContractFJ(fj.getTargetFileId());
            FileUtil.delFile(path + fj.getUrl().split(SysConstant.FILE_HTTPURL + "/")[1]);
        }

        String originalImgname = "";
        String url = "";
        for (MultipartFile item : FILE) {
            originalImgname = item.getOriginalFilename();//原始名称

            if (originalImgname != null && originalImgname.length() > 0) {
                logger.info("文件上传:" + originalImgname);
                try {
                    //上传sftp服务器
                    String path = "/contract/" + contractId + "/temp/fj";
                    url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                            DateUtil.getDays(), originalImgname, path);
                    //上传sftp
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    ContractFiles contractFile = new ContractFiles();
                    contractFile.setContractId(contractId);
                    contractFile.setModelFileId(null);
                    contractFile.setFileName(originalImgname);
                    contractFile.setUrl(url);
                    fjList.add(contractFile);
                }
            }
        }

        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            //保存附件到数据库
            if (fjList.size() > 0) {
                contractFilesService.save(fjList);
            }
        }
        //转pdf并盖章
        logger.info("转pdf并盖章");
        List<ContractFiles> contractFilesList = contractFilesService.findWordByContractId(contractId);
        for (int i = 0; i < contractFilesList.size(); i++) {
            ContractFiles file = contractFilesList.get(i);
            int modelFileId = file.getModelFileId();
            //合同模板路径
            String wordUrl = file.getUrl();
            String wordPath = path + wordUrl.split(SysConstant.FILE_HTTPURL + "/")[1];
            logger.info("wordPath=" + wordPath);
            //要生成pdf的路径
            String path2 = "contract/" + contractId   + "/temp/pdf/first/";
            FileUtil.createDir(path + path2);
            String fileName = file.getFileName();
            String pdfPath = path + path2 + fileName;
            logger.info("pdfPath=" + pdfPath);
            File pdfFile = new File(pdfPath + ".pdf");
            if (pdfFile.exists()){
                FileUtil.delFile(pdfPath + ".pdf");
                Word2PdfUtils.wordToPdf(wordPath, pdfPath + ".pdf");
            }else{
                Word2PdfUtils.wordToPdf(wordPath, pdfPath + ".pdf");
            }
            //pdf盖章后存放路径
            String outputPath = pdfPath + "_1.pdf";
            List<ContractStamp> contractStampList = contractStampService.findFxByModelFileId(modelFileId);
            File stampFile = new File(outputPath);
            if(stampFile.exists()){
                FileUtil.delFile(outputPath);
                PdfStampUtils.setWatermark(pdfPath + ".pdf", contractStampList, outputPath);
            }else {
                PdfStampUtils.setWatermark(pdfPath + ".pdf", contractStampList, outputPath);
            }
            String pdfUrl = SysConstant.FILE_HTTPURL + "/" + path2 + fileName + "_1.pdf";
            file.setUrl(pdfUrl);
            contractFilesService.updateUrl(file);
        }


        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            Contract contract = contractService.findByContractId(contractId);
            //合同正式生效
            contract.setStatus(1);//1-待受托管理人审核 2-待风控审核 3-驳回 4-已归档
            contractService.updateStatus(contract);
            ContractTemplate contractTemplate = contractTemplateService.findByTemplateId(contract.getTemplateId());
            contractTemplate.setStatus(1);//0-未发行 1-已发行
            contractTemplateService.update(contractTemplate);
            //记录操作日志
            OprationRecord oprationRecord = new OprationRecord();
            oprationRecord.setContractId(contract.getContractId());
            oprationRecord.setOpType(1);//发行
            oprationRecord.setOpId(getUser().getUSER_ID());
            oprationRecord.setOpTime(DateUtil.getTime());
            oprationRecordService.save(oprationRecord);
        }

        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * ajax判断合同编号是否已存在
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/checkContractNo.do", method = {RequestMethod.POST})
    public void checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        PageData pd = new PageData();
        String contractNo = request.getParameter("contractNo");
        Integer contractId = Integer.parseInt(request.getParameter("contractId"));
        if (contractNo.trim().equals("")) {
            out.print(2);// 2是不能为空
        } else {
            pd.put("contractNo",contractNo);
            pd.put("contractId",contractId);
            List<Contract> contractNoList = contractService.checkContractNo(pd);
            if (contractNoList != null && contractNoList.size() > 0) {
                out.print(1);// 1用户名已存在
            } else {
                out.print(3);// 用户名可以用
            }
        }
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
