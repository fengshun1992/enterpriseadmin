package com.admin.controller.enterprise.contract;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.OrgMenbers;
import com.admin.entity.system.User;
import com.admin.service.enterprise.contract.ContractService;
import com.admin.service.enterprise.orgMenbers.OrgMenbersService;
import com.admin.service.enterprise.orgnation.OrgnationService;
import com.admin.util.Const;
import com.admin.util.PageData;
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
@RequestMapping(value = "/contract")
public class ContractController extends BaseController {

    String menuUrl = "contract/list.do"; // 菜单地址(权限用)
    @Resource(name = "contractService")
    private ContractService contractService;
    @Resource(name = "orgMenbersService")
    private OrgMenbersService orgMenbersService;
    @Resource(name = "orgnationService")
    private OrgnationService orgnationService;

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, "显示contract列表");
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
            mv.setViewName("enterprise/contract/contract_list");
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
            return mv;
        }
        pd.put("orgId", orgMenbers.getOrgId());
        page.setPd(pd);

        List<PageData> contractList = contractService.listPdPage(page);
        List<PageData> orgnationList = orgnationService.allList(pd);

        mv.setViewName("enterprise/contract/contract_list");
        mv.addObject("contractList", contractList);
        mv.addObject("orgnationList", orgnationList);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
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
