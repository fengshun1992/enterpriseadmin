package com.admin.controller.enterprise.stAuditContract;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.*;
import com.admin.entity.system.User;
import com.admin.service.enterprise.contract.ContractService;
import com.admin.service.enterprise.contractFilesService.ContractFilesService;
import com.admin.service.enterprise.contractStamp.ContractStampService;
import com.admin.service.enterprise.oprationRecord.OprationRecordService;
import com.admin.service.enterprise.orgMenbers.OrgMenbersService;
import com.admin.service.enterprise.orgnation.OrgnationService;
import com.admin.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/stAuditContract")
public class StAuditContractController extends BaseController {

    String menuUrl = "stAuditContract/list.do"; // 菜单地址(权限用)
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
    @Resource(name = "contractStampService")
    private ContractStampService contractStampService;

    String path = SysConstant.LOCAL_FILE;

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, "显示stAuditContract列表");
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
            mv.setViewName("enterprise/auditContract/stAuditContract_list");
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
            return mv;
        }
        pd.put("orgId", orgMenbers.getOrgId());
        pd.put("status", 1);//待受托审核
        page.setPd(pd);

        List<PageData> contractList = contractService.stListPage(page);
        List<PageData> orgnationList = orgnationService.allList(pd);

        mv.setViewName("enterprise/auditContract/stAuditContract_list");
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
    public ModelAndView goEdit() throws Exception {
        logBefore(logger, "去修改stAuditContract页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<ContractFiles> contractFilesList = contractFilesService.findById(pd);
        pd = contractService.findById(pd);
        mv.setViewName("enterprise/auditContract/stAuditContract_edit");
        mv.addObject("contractFilesList", contractFilesList);
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit(Contract contract) throws Exception {
        logBefore(logger, "修改stAuditContract");
        ModelAndView mv = this.getModelAndView();

        int contractId = contract.getContractId();
        contract.setUpdateTime(DateUtil.getTime());
        if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            //盖受托公司章
            if (contract.getStatus() == 2) {
                List<ContractFiles> files = contractFilesService.findWordByContractId(contractId);
                for (ContractFiles file : files) {
                    int modelFileId = file.getModelFileId();
                    String fileName = file.getFileName();
                    String url = file.getUrl();

                    String inputPath = path + url.split(SysConstant.FILE_HTTPURL + "/")[1];
                    String outputPath = "contract/" + contractId + "/temp/pdf/second/";
                    FileUtil.createDir(path + outputPath);
                    List<ContractStamp> stamps = contractStampService.findStByModelFileId(modelFileId);
                    PdfStampUtils.setWatermark(inputPath, stamps, path + outputPath + fileName + ".pdf");
                    file.setUrl(SysConstant.FILE_HTTPURL + "/" + outputPath + fileName + ".pdf");
                    //更新url
                    contractFilesService.updateUrl(file);
                }
            }

            contractService.update(contract);
            //记录操作日志
            OprationRecord oprationRecord = new OprationRecord();
            oprationRecord.setContractId(contractId);
            oprationRecord.setOpType(contract.getStatus());//2-审核通过 3-驳回
            oprationRecord.setReason(contract.getReason());
            oprationRecord.setOpId(getUser().getUSER_ID());
            oprationRecord.setOpTime(DateUtil.getTime());
            oprationRecordService.save(oprationRecord);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
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
