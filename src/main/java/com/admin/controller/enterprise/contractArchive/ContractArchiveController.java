package com.admin.controller.enterprise.contractArchive;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.Contract;
import com.admin.entity.enterprise.ContractArchive;
import com.admin.entity.enterprise.OprationRecord;
import com.admin.entity.enterprise.OrgMenbers;
import com.admin.entity.system.User;
import com.admin.service.enterprise.contract.ContractService;
import com.admin.service.enterprise.contractArchive.ContractArchiveService;
import com.admin.service.enterprise.oprationRecord.OprationRecordService;
import com.admin.service.enterprise.orgMenbers.OrgMenbersService;
import com.admin.service.enterprise.orgnation.OrgnationService;
import com.admin.service.system.user.UserService;
import com.admin.util.Const;
import com.admin.util.DateUtil;
import com.admin.util.PageData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/contractArchive")
public class ContractArchiveController extends BaseController {

    String menuUrl = "contractArchive/list.do"; // 菜单地址(权限用)
    @Resource(name = "contractArchiveService")
    private ContractArchiveService contractArchiveService;
    @Resource(name = "contractService")
    private ContractService contractService;
    @Resource(name = "orgMenbersService")
    private OrgMenbersService orgMenbersService;
    @Resource(name = "orgnationService")
    private OrgnationService orgnationService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "oprationRecordService")
    private OprationRecordService oprationRecordService;

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, "显示contractArchive列表");
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
            pd.put("type", 1);//1-发行中 2-下架中
        }
        page.setPd(pd);

        List<PageData> contractList = contractArchiveService.gdListPage(page);
        List<PageData> orgnationList = orgnationService.allList(pd);

        mv.setViewName("enterprise/contractArchive/contractArchive_list");
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
        logBefore(logger, "去修改contractArchive页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        pd = contractArchiveService.findById(pd);
        List<PageData> allUserList = userService.listAllUser(new PageData());

        mv.setViewName("enterprise/contractArchive/contractArchive_edit");
        mv.addObject("allUserList", allUserList);
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * ajax判断合同编号是否已存在
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/checkContractNo.do", method = {RequestMethod.POST})
    public void checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        int contractId = Integer.parseInt(request.getParameter("contractId"));
        List<Contract> list = getContracts(contractId);
        if (list == null || list.size() == 0) {
            out.print(1);// 合同编号可以用
        } else {
            out.print(2);// 合同编号已存在
        }
    }

    /**
     * 获取合同列表
     * @param contractId
     * @return
     * @throws Exception
     */
    private List<Contract> getContracts(int contractId) throws Exception {
        Contract contract = contractService.findByContractId(contractId);
        String contractNo = contract.getContractNo();
        PageData pd = new PageData();
        pd.put("contractId", contractId);
        pd.put("contractNo", contractNo);

        return contractService.checkContractNo(pd);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit(ContractArchive contractArchive) throws Exception {
        logBefore(logger, "修改stAuditContract");
        ModelAndView mv = this.getModelAndView();

        int contractId = contractArchive.getContractId();
        int type = contractArchive.getType();//1-发行 2-下架
        int status = type == 2 ? 5 : 6;//如果type为下架，则进行的是下架操作；5:下架 6:重新发行

        //重新发行
        if (type == 1) {
            List<Contract> list = getContracts(contractId);
            if (list != null && list.size() > 0) {
                String reason = "合同重新发行失败,合同编号重复!";
                logger.info(reason);
                mv.addObject("msg", "failed");
                mv.addObject("reason", reason);
                mv.setViewName("save_result");
                return mv;
            }
        }

        contractArchive.setUserId(getUser().getUSER_ID());
        contractArchive.setOpTime(DateUtil.getTime());
        contractArchiveService.update(contractArchive);

        Contract contract = contractService.findByContractId(contractId);
        contract.setStatus(status);
        contractService.update(contract);

        OprationRecord oprationRecord = new OprationRecord();
        oprationRecord.setContractId(contractId);
        oprationRecord.setOpType(status);
        oprationRecord.setReason(contractArchive.getReason());
        oprationRecord.setOpId(getUser().getUSER_ID());
        oprationRecord.setOpTime(DateUtil.getTime());
        oprationRecordService.save(oprationRecord);

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
