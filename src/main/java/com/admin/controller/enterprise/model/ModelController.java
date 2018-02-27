package com.admin.controller.enterprise.model;

import com.admin.controller.base.BaseController;
import com.admin.entity.Page;
import com.admin.entity.enterprise.*;
import com.admin.entity.system.User;
import com.admin.service.enterprise.fileParams.FileParamsService;
import com.admin.service.enterprise.model.ModelService;
import com.admin.service.enterprise.modelFiles.ModelFilesService;
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
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/model")
public class ModelController extends BaseController {

    String menuUrl = "model/list.do"; // 菜单地址(权限用)
    @Resource(name = "modelService")
    private ModelService modelService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "modelFilesService")
    private ModelFilesService modelFilesService;
    @Resource(name = "fileParamsService")
    private FileParamsService fileParamsService;

    String Path = "/model";

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(Model model, @RequestParam(value = "FILE", required = false) MultipartFile[] FILE) throws Exception {
        logBefore(logger, "新增model");
        ModelAndView mv = this.getModelAndView();

        String fileType = "";
        List<ModelFiles> modelFiles = new ArrayList<>();

        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            model.setCreatorId(getUser().getUSER_ID());
            model.setCreateTime(DateUtil.getTime());
            modelService.save(model);
        } // 判断新增权限

        String originalImgname = "";
        String url = "";
        for (MultipartFile item : FILE) {
            originalImgname = item.getOriginalFilename();//原始名称

            if (originalImgname != null && originalImgname.length() > 0) {
                fileType = originalImgname.substring(originalImgname.lastIndexOf("."));
                if (".docx".equals(fileType)) {
                    logger.info("文件上传:" + originalImgname);
                    try {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/" + model.getName());
                        //上传sftp
                    } catch (Exception e) {
                        logger.error(e.toString(), e);
                    } finally {
                        ModelFiles file = new ModelFiles();
                        file.setModelId(model.getModelId());
                        file.setFileName(originalImgname.substring(0, originalImgname.length() - 5));//去掉.docx
                        file.setUrl(url);
                        modelFiles.add(file);
                    }
                }
            }
        }

        if (modelFiles.size() > 0) {
            modelFilesService.save(modelFiles);
            modelFiles = modelFilesService.getList();
            saveParams(FILE, modelFiles);
        }

        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 保存上传文件的参数
     *
     * @param FILE
     * @param modelFiles
     * @throws Exception
     */
    private void saveParams(MultipartFile[] FILE, List<ModelFiles> modelFiles) throws Exception {
        String originalImgname = "";//文件名
        String fileType = "";//文档类型
        FileParams fileParams = null;
        List<FileParams> fileParamsList = new ArrayList<>();

        for (int i = 0; i < FILE.length; i++) {
            int modelFileId = modelFiles.get(i).getModelFileId();
            originalImgname = FILE[i].getOriginalFilename();//原始名称
            if (originalImgname != null && originalImgname.length() > 0) {
                fileType = originalImgname.substring(originalImgname.lastIndexOf("."));
                if (".docx".equals(fileType)) {
                    InputStream is = FILE[i].getInputStream();
                    Map map = WordTemplateUtils.getWord(is);

                    List<String> textList = (List<String>) map.get("textList");
                    if (textList != null && textList.size() > 0) {
                        for (int j = 0; j < textList.size(); j++) {
                            fileParams = new FileParams();
                            fileParams.setModelFileId(modelFileId);
                            fileParams.setParam(textList.get(j));
                            fileParams.setParamType(1);//文本
                            fileParamsList.add(fileParams);
                        }
                    }

                    List<WordTable> tableList = (List<WordTable>) map.get("tableList");
                    if (tableList != null && tableList.size() > 0) {
                        for (int k = 0; k < tableList.size(); k++) {
                            List<TableCell> columnList = tableList.get(k).getColumnList();
                            for (int l = 0; l < columnList.size(); l++) {
                                fileParams = new FileParams();
                                fileParams.setModelFileId(modelFileId);
                                fileParams.setParam(columnList.get(l).getParam());
                                fileParams.setParamType(2);//表格
                                fileParams.setTableName(tableList.get(k).getTableName());
                                fileParams.setColumnIndex(columnList.get(l).getColumnIndex());
                                fileParamsList.add(fileParams);
                            }
                        }
                    }

                    if (textList.size() == 0 && tableList.size() == 0) {
                        fileParams = new FileParams();
                        fileParams.setModelFileId(modelFileId);
                        fileParams.setParamType(0);
                        fileParamsList.add(fileParams);
                    }
                } else {
                    fileParams = new FileParams();
                    fileParams.setModelFileId(modelFileId);
                    fileParams.setParamType(0);
                    fileParamsList.add(fileParams);
                }
            }
        }
        fileParamsService.save(fileParamsList);
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit(Model model, @RequestParam(value = "FILE", required = false) MultipartFile[] FILE) throws Exception {
        logBefore(logger, "修改model");
        ModelAndView mv = this.getModelAndView();

        String fileType = "";
        List<ModelFiles> modelFiles = new ArrayList<>();
        int modelId = model.getModelId();

        String originalImgname = "";
        String url = "";
        for (MultipartFile item : FILE) {
            originalImgname = item.getOriginalFilename();//原始名称
            if (originalImgname != null && originalImgname.length() > 0) {
                fileType = originalImgname.substring(originalImgname.lastIndexOf("."));
                if (".docx".equals(fileType)) {
                    logger.info("文件上传:" + originalImgname);
                    try {
                        //上传sftp服务器
                        url = new SftpFileUtils().SFTPFileForUeditor(item.getInputStream(),
                                DateUtil.getDays(), originalImgname, Path + "/" + model.getName());
                        //上传sftp
                    } catch (Exception e) {
                        logger.error(e.toString(), e);
                    } finally {
                        ModelFiles file = new ModelFiles();
                        file.setModelId(modelId);
                        file.setFileName(originalImgname.substring(0, originalImgname.length() - 5));//去掉.docx
                        file.setUrl(url);
                        modelFiles.add(file);
                    }
                }
            }
        }

        if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            model.setEditorId(getUser().getUSER_ID());
            model.setUpdateTime(DateUtil.getTime());
            modelService.update(model);
            if (modelFiles.size() > 0) {
                fileParamsService.delete(modelId);
                modelFilesService.delete(modelId);
                modelFilesService.save(modelFiles);
                modelFiles = modelFilesService.getList();
                saveParams(FILE, modelFiles);
            }
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
        logBefore(logger, "去修改model页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = modelService.findById(pd);
        List<PageData> modelFilesList = modelFilesService.findById(pd);

        mv.setViewName("enterprise/model/model_edit");
        mv.addObject("modelFilesList", modelFilesList);
        mv.addObject("userName", getUser().getUSERNAME());
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        logBefore(logger, "去model新增页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        mv.setViewName("enterprise/model/model_edit");
        mv.addObject("userName", getUser().getUSERNAME());
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 显示列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, "显示model列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        List<PageData> modelList = modelService.listPdPage(page);
        List<PageData> allUserList = userService.listAllUser(pd);

        mv.setViewName("enterprise/model/model_list");
        mv.addObject("modelList", modelList);
        mv.addObject("allUserList", allUserList);
        mv.addObject("userName", getUser().getUSERNAME());
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除model");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
                Integer templateId = Integer.parseInt(pd.getString("ID"));
                fileParamsService.delete(templateId);
                modelFilesService.delete(templateId);
                modelService.delete(pd);
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
