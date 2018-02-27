package com.admin.controller.enterprise.orgnation;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.ContractTemplate;
import com.admin.entity.enterprise.OrgMenbers;
import com.admin.entity.enterprise.Orgnation;
import com.admin.service.enterprise.contractTemplate.ContractTemplateService;
import com.admin.service.enterprise.orgMenbers.OrgMenbersService;
import com.admin.service.enterprise.orgnation.OrgnationService;
import com.admin.service.system.user.UserService;
import com.admin.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/orgnation")
public class OrgnationController extends BaseController {

    String menuUrl = "orgnation/list.do"; // 菜单地址(权限用)

    @Resource(name = "orgnationService")
    private OrgnationService orgnationService;
    @Resource(name = "orgMenbersService")
    private OrgMenbersService orgMenbersService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "contractTemplateService")
    private ContractTemplateService contractTemplateService;

    String Path = "/sign";

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAddU() throws Exception {
        logBefore(logger, "去orgnation新增页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> allUserList = userService.listAllUser(pd);
        //过滤管理人员,不能一个人担任两个职位
        List<OrgMenbers> orgMenbersList = orgMenbersService.getAllUserId();
        for (OrgMenbers orgMenber : orgMenbersList) {
            Long userId = orgMenber.getUserId();
            for (int j = allUserList.size() - 1; j >= 0; j--) {
                Long USER_ID = (Long) allUserList.get(j).get("USER_ID");
                if (userId.equals(USER_ID)) {
                    allUserList.remove(j);
                }
            }

        }

        mv.setViewName("enterprise/orgnation/orgnation_edit");
        mv.addObject("allUserList", allUserList);
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveU(Orgnation orgnation,
                              @RequestParam(value = "FILE", required = false) MultipartFile[] FILE,
                              String userIds) throws Exception {
        logBefore(logger, "新增orgnation");
        ModelAndView mv = this.getModelAndView();

        //文件上传
        String originalImgname = "";
        String url = "";
        for (MultipartFile item : FILE) {
            originalImgname = item.getOriginalFilename();//原始名称

            if (originalImgname != null && originalImgname.length() > 0) {
                logger.info("文件上传:" + originalImgname);
                try {
                    if (orgnation.getOrgType() == 1) {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/fx/" + orgnation.getOrgName());
                        //上传sftp
                    } else if (orgnation.getOrgType() == 2) {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/st/" + orgnation.getOrgName());
                        //上传sftp
                    } else if (orgnation.getOrgType() == 3) {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/fk/" + orgnation.getOrgName());
                        //上传sftp
                    }
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                }
            }
        }

        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            orgnation.setSignImageUrl(url);
            orgnationService.save(orgnation);
            int orgId = orgnation.getOrgId();
            if (StringUtils.isNotEmpty(userIds)) {
                String[] ids = userIds.split(",");
                List<OrgMenbers> list = new ArrayList<>();
                for (String id : ids) {
                    OrgMenbers orgMenbers = new OrgMenbers();
                    orgMenbers.setOrgId(orgId);
                    orgMenbers.setUserId(Long.parseLong(id));
                    list.add(orgMenbers);
                }
                orgMenbersService.save(list);
            }
        } // 判断新增权限
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEditU() throws Exception {
        logBefore(logger, "去修改orgnation页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd2 = new PageData();
        pd2 = this.getPageData();
        Orgnation pd = orgnationService.findByOId(pd2);
        List<PageData> allUserList = userService.listAllUser(pd2);
        //过滤管理人员,不能一个人担任两个职位
        List<OrgMenbers> orgMenbersList = orgMenbersService.getAllUserId();
        List<OrgMenbers> orgMenbersList2 = pd.getOrgUser();

        for (int i = 0; i < orgMenbersList2.size(); i++) {
            for (int j = orgMenbersList.size()-1; j >=0 ; j--) {
                if (orgMenbersList.get(j).getUserId().equals(orgMenbersList2.get(i).getUserId())) {
                    orgMenbersList.remove(j);
                }
            }
        }

        for (OrgMenbers orgMenber : orgMenbersList) {
            Long userId = orgMenber.getUserId();
            for (int j = allUserList.size() - 1; j >= 0; j--) {
                Long USER_ID = (Long) allUserList.get(j).get("USER_ID");
                if (userId.equals(USER_ID)) {
                    allUserList.remove(j);
                }
            }
        }
        mv.setViewName("enterprise/orgnation/orgnation_edit");
        mv.addObject("allUserList", allUserList);
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView editU(Orgnation orgnation,
                              @RequestParam(value = "FILE", required = false) MultipartFile[] FILE,
                              String userIds) throws Exception {
        logBefore(logger, "修改orgnation");
        ModelAndView mv = this.getModelAndView();

        //修改风控需要特别注意
        if (orgnation.getOrgType() == 3) {
            PageData pd = new PageData();
            pd.put("ID", orgnation.getOrgId());
            Orgnation oldOrgnation = orgnationService.findByOId(pd);
            List<OrgMenbers> orgMenbersList = oldOrgnation.getOrgUser();
            if (orgMenbersList != null && orgMenbersList.size() > 0) {
                for (OrgMenbers item : orgMenbersList) {
                    Long userId = item.getUserId();
                    List<ContractTemplate> list = contractTemplateService.findByCreatorId(userId);
                    if (list != null && list.size() > 0) {
                        //如果修改前的userId创建了合同模板，则必须先修改合同模板的创建人，才能从机构管理方移除该userId
                        if (!userIds.contains(userId.toString())) {
                            String reason = "需先修改合同模板创建人，才能移除该工作人员Id";
                            logger.info(reason);
                            mv.addObject("msg", "failed");
                            mv.addObject("reason", reason);
                            mv.setViewName("save_result");
                            return mv;
                        }
                    }
                }
            }
        }

        String originalImgname = "";
        String url = "";
        for (MultipartFile item : FILE) {
            originalImgname = item.getOriginalFilename();//原始名称

            if (originalImgname != null && originalImgname.length() > 0) {
                logger.info("文件上传:" + originalImgname);
                try {
                    if (orgnation.getOrgType() == 1) {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/fx/" + orgnation.getOrgName());
                        //上传sftp
                    } else if (orgnation.getOrgType() == 2) {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/st/" + orgnation.getOrgName());
                        //上传sftp
                    } else if (orgnation.getOrgType() == 3) {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/fk/" + orgnation.getOrgName());
                        //上传sftp
                    }
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                }
            }
        }

        int orgId = orgnation.getOrgId();
        if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            orgnation.setSignImageUrl(url);
            orgnationService.update(orgnation);
            if (StringUtils.isNotEmpty(userIds)) {
                String[] ids = userIds.split(",");
                List<OrgMenbers> list = new ArrayList<>();
                for (String id : ids) {
                    OrgMenbers orgMenbers = new OrgMenbers();
                    orgMenbers.setOrgId(orgId);
                    orgMenbers.setUserId(Long.parseLong(id));
                    list.add(orgMenbers);
                }
                orgMenbersService.delete(orgId);
                orgMenbersService.save(list);
            }
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView listPublishers(Page page) throws Exception {
        logBefore(logger, "显示orgnation列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);

        List<PageData> orgnationList = orgnationService.listPage(page);
        List<PageData> allUserList = userService.listAllUser(pd);
        mv.setViewName("enterprise/orgnation/orgnation_list");
        mv.addObject("orgnationList", orgnationList);
        mv.addObject("allUserList", allUserList);
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void deleteU(PrintWriter out) {
        logBefore(logger, "删除orgnation");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
                //删除机构
                orgnationService.delete(pd);
                //删除关系表
                int orgId = Integer.parseInt(pd.getString("ID"));
                orgMenbersService.delete(orgId);
            }
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /* ===============================权限================================== */
    public Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */
}
