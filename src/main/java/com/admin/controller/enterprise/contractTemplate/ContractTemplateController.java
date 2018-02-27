package com.admin.controller.enterprise.contractTemplate;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractTemplate;
import com.admin.entity.enterprise.OrgMenbers;
import com.admin.entity.system.User;
import com.admin.service.enterprise.contractTemplate.ContractTemplateService;
import com.admin.service.enterprise.model.ModelService;
import com.admin.service.enterprise.orgMenbers.OrgMenbersService;
import com.admin.service.enterprise.orgnation.OrgnationService;
import com.admin.service.system.user.UserService;
import com.admin.util.Const;
import com.admin.util.DateUtil;
import com.admin.util.Jurisdiction;
import com.admin.util.PageData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/contractTemplate")
public class ContractTemplateController extends BaseController {

    String menuUrl = "contractTemplate/list.do"; // 菜单地址(权限用)
    @Resource(name = "contractTemplateService")
    private ContractTemplateService contractTemplateService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "orgnationService")
    private OrgnationService orgnationService;
    @Resource(name = "orgMenbersService")
    private OrgMenbersService orgMenbersService;
    @Resource(name = "modelService")
    private ModelService modelService;

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(ContractTemplate contractTemplate) throws Exception {
        logBefore(logger, "新增contractTemplate");
        ModelAndView mv = this.getModelAndView();

        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            contractTemplate.setStatus(0);//未使用
            contractTemplate.setCreatorId(getUser().getUSER_ID());
            contractTemplate.setCreateTime(DateUtil.getTime());
            contractTemplateService.save(contractTemplate);
        } // 判断新增权限

        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit(ContractTemplate contractTemplate) throws Exception {
        logBefore(logger, "修改contractTemplate");
        ModelAndView mv = this.getModelAndView();

        if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            contractTemplateService.update(contractTemplate);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        logBefore(logger, "去修改contractTemplate页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = contractTemplateService.findById(pd);
        List<PageData> orgnationList = orgnationService.allList(pd);
        List<PageData> modelList = modelService.listAll();

        mv.setViewName("enterprise/contractTemplate/contractTemplate_edit");
        mv.addObject("orgnationList", orgnationList);
        mv.addObject("modelList", modelList);
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        logBefore(logger, "去contractTemplate新增页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> orgnationList = orgnationService.allList(pd);
        List<PageData> modelList = modelService.listAll();

        mv.setViewName("enterprise/contractTemplate/contractTemplate_edit");
        mv.addObject("orgnationList", orgnationList);
        mv.addObject("modelList", modelList);
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, "显示contractTemplate列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Long userId = getUser().getUSER_ID();
        pd.put("userId", userId);
        OrgMenbers orgMenbers = orgMenbersService.findByUserId(pd);
        if (orgMenbers == null) {
            mv.setViewName("enterprise/contractTemplate/contractTemplate_list");
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
            return mv;
        }
        pd.put("orgId", orgMenbers.getOrgId());
        page.setPd(pd);

        List<PageData> contractTemplateList = contractTemplateService.listPdPage(page);
        List<PageData> orgnationList = orgnationService.allList(pd);
        List<PageData> allUserList = userService.listAllUser(pd);
        List<PageData> modelList = modelService.listAll();

        mv.setViewName("enterprise/contractTemplate/contractTemplate_list");
        mv.addObject("contractTemplateList", contractTemplateList);
        mv.addObject("orgnationList", orgnationList);
        mv.addObject("allUserList", allUserList);
        mv.addObject("modelList", modelList);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除contractTemplate");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
                contractTemplateService.delete(pd);
            }
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
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
