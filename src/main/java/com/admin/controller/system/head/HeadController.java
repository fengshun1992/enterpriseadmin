package com.admin.controller.system.head;

import com.admin.controller.base.BaseController;
import com.admin.data.ResultCode;
import com.admin.data.ResultData;
import com.admin.service.system.template.HttpRequestDomainService;
import com.admin.service.system.user.UserService;
import com.admin.util.*;
import com.admin.util.rest.WebRestClient;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/head")
public class HeadController extends BaseController {

    @Resource(name = "userService")
    private UserService userService;
    @Autowired
    public HttpRequestDomainService httpRequestDomainService;

    String apiUrl = "http://192.168.11.117:8080/sms/sends";
    WebRestClient webRestClient = new WebRestClient(apiUrl);




    /**
     * 获取头部信息
     */
    @RequestMapping(value = "/getUname")
    @ResponseBody
    public Object getList() {
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();

            // shiro管理的session
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();

            PageData pds = new PageData();
            pds = (PageData) session.getAttribute(Const.SESSION_userpds);

            if (null == pds) {
                String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString(); // 获取当前登录者loginname
                pd.put("USERNAME", USERNAME);
                pds = userService.findByUId(pd);
                session.setAttribute(Const.SESSION_userpds, pds);
            }

            pdList.add(pds);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 保存皮肤
     */
    @RequestMapping(value = "/setSKIN")
    public void setSKIN(PrintWriter out) {
        PageData pd = new PageData();
        try {
            pd = this.getPageData();

            // shiro管理的session
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();

            String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();// 获取当前登录者loginname
            pd.put("USERNAME", USERNAME);
            userService.setSKIN(pd);
            session.removeAttribute(Const.SESSION_userpds);
            session.removeAttribute(Const.SESSION_USERROL);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 去编辑邮箱页面
     */
    @RequestMapping(value = "/editEmail")
    public ModelAndView editEmail() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("system/head/edit_email");
        mv.addObject("pd", pd);
        return mv;
    }


    class MyThread implements Runnable {
        JSONObject json = null;
        int index = 0;

        public MyThread(JSONObject json, int index) {
            this.json = json;
            this.index = index;
        }

        public void run() {
            logger.info("----------------------------" + index);
            logger.info("++++++++++++++++++++++++++++++++" + json.get("tels"));
            httpRequestDomainService.sendPost(json.toString(), "send");
        }
    }

    protected ResultData getFailReult(ResultData resultData, String message) {
        resultData.setResult(ResultCode.FAIL);
        resultData.setMsg(message);
        return resultData;
    }

    /**
     * 去发送电子邮件页面
     */
    @RequestMapping(value = "/goSendEmail")
    public ModelAndView goSendEmail() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("system/head/send_email");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去系统设置页面
     */
    @RequestMapping(value = "/goSystem")
    public ModelAndView goEditEmail() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("YSYNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
        pd.put("COUNTPAGE", Tools.readTxtFile(Const.PAGE)); // 读取每页条数
        String strEMAIL = Tools.readTxtFile(Const.EMAIL); // 读取邮件配置
        String strSMS1 = Tools.readTxtFile(Const.SMS1); // 读取短信1配置
        String strSMS2 = Tools.readTxtFile(Const.SMS2); // 读取短信2配置
        String strFWATERM = Tools.readTxtFile(Const.FWATERM); // 读取文字水印配置
        String strIWATERM = Tools.readTxtFile(Const.IWATERM); // 读取图片水印配置
        pd.put("Token", Tools.readTxtFile(Const.WEIXIN)); // 读取微信配置

        if (null != strEMAIL && !"".equals(strEMAIL)) {
            String strEM[] = strEMAIL.split(",fh,");
            if (strEM.length == 4) {
                pd.put("SMTP", strEM[0]);
                pd.put("PORT", strEM[1]);
                pd.put("EMAIL", strEM[2]);
                pd.put("PAW", strEM[3]);
            }
        }

        if (null != strSMS1 && !"".equals(strSMS1)) {
            String strS1[] = strSMS1.split(",fh,");
            if (strS1.length == 2) {
                pd.put("SMSU1", strS1[0]);
                pd.put("SMSPAW1", strS1[1]);
            }
        }

        if (null != strSMS2 && !"".equals(strSMS2)) {
            String strS2[] = strSMS2.split(",fh,");
            if (strS2.length == 2) {
                pd.put("SMSU2", strS2[0]);
                pd.put("SMSPAW2", strS2[1]);
            }
        }

        if (null != strFWATERM && !"".equals(strFWATERM)) {
            String strFW[] = strFWATERM.split(",fh,");
            if (strFW.length == 5) {
                pd.put("isCheck1", strFW[0]);
                pd.put("fcontent", strFW[1]);
                pd.put("fontSize", strFW[2]);
                pd.put("fontX", strFW[3]);
                pd.put("fontY", strFW[4]);
            }
        }

        if (null != strIWATERM && !"".equals(strIWATERM)) {
            String strIW[] = strIWATERM.split(",fh,");
            if (strIW.length == 4) {
                pd.put("isCheck2", strIW[0]);
                pd.put("imgUrl", strIW[1]);
                pd.put("imgX", strIW[2]);
                pd.put("imgY", strIW[3]);
            }
        }

        mv.setViewName("system/head/sys_edit");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 保存系统设置1
     */
    @RequestMapping(value = "/saveSys")
    public ModelAndView saveSys() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Tools.writeFile(Const.SYSNAME, pd.getString("YSYNAME")); // 写入系统名称
        Tools.writeFile(Const.PAGE, pd.getString("COUNTPAGE")); // 写入每页条数
        Tools.writeFile(Const.EMAIL, pd.getString("SMTP") + ",fh," + pd.getString("PORT") + ",fh," + pd.getString("EMAIL") + ",fh," + pd.getString("PAW")); // 写入邮件服务器配置
        Tools.writeFile(Const.SMS1, pd.getString("SMSU1") + ",fh," + pd.getString("SMSPAW1")); // 写入短信1配置
        Tools.writeFile(Const.SMS2, pd.getString("SMSU2") + ",fh," + pd.getString("SMSPAW2")); // 写入短信2配置
        mv.addObject("msg", "OK");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 保存系统设置2
     */
    @RequestMapping(value = "/saveSys2")
    public ModelAndView saveSys2() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Tools.writeFile(Const.FWATERM, pd.getString("isCheck1") + ",fh," + pd.getString("fcontent") + ",fh," + pd.getString("fontSize") + ",fh," + pd.getString("fontX") + ",fh," + pd.getString("fontY")); // 文字水印配置
        Tools.writeFile(Const.IWATERM, pd.getString("isCheck2") + ",fh," + pd.getString("imgUrl") + ",fh," + pd.getString("imgX") + ",fh," + pd.getString("imgY")); // 图片水印配置
//        Watermark.fushValue();
        mv.addObject("msg", "OK");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 保存系统设置3
     */
    @RequestMapping(value = "/saveSys3")
    public ModelAndView saveSys3() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Tools.writeFile(Const.WEIXIN, pd.getString("Token")); // 写入微信配置
        mv.addObject("msg", "OK");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去代码生成器页面
     */
    @RequestMapping(value = "/goProductCode")
    public ModelAndView goProductCode() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/head/productCode");
        return mv;
    }


    /**
     * 去新增短信模板页面
     */
    @RequestMapping(value = "/goAddT")
    public ModelAndView goAddT() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        mv.setViewName("system/head/template_edit");
        mv.addObject("msg", "saveT");
        mv.addObject("pd", pd);
//		mv.addObject("roleList", roleList);

        return mv;
    }

    /**
     * 去修改短信模板页面
     */
    @RequestMapping(value = "/goEditT")
    public ModelAndView goEditT() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String templateid = pd.getString("templateid");
        BigDecimal id = new BigDecimal(templateid);
//		List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色
//		pd = userService.findByUiId(pd); // 根据ID读取
//		pd = templateService.findByTemId(id); // 列出id模板
        String content = StringUtil.getClobtoStr((Clob) pd.get("SMS_TEMPLATE"));
        pd.put("SMS_TEMPLATE", content);
        mv.setViewName("system/head/template_edit");
        mv.addObject("msg", "editT");
        mv.addObject("pd", pd);
//		mv.addObject("roleList", roleList);

        return mv;
    }
}
