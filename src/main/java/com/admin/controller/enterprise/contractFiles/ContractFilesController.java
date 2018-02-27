package com.admin.controller.enterprise.contractFiles;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.system.User;
import com.admin.service.enterprise.contractFilesService.ContractFilesService;
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
@RequestMapping(value = "/contractFiles")
public class ContractFilesController extends BaseController {

    String menuUrl = "contractFiles/list.do"; // 菜单地址(权限用)
    @Resource(name = "contractFilesService")
    private ContractFilesService contractFilesService;

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, "显示contractFiles列表");
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
        page.setPd(pd);

        List<PageData> contractFilesList = contractFilesService.listPage(page);

        mv.setViewName("enterprise/contractFiles/contractFiles_list");
        mv.addObject("contractFilesList", contractFilesList);
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
