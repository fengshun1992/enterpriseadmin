package com.admin.controller.system.login;

import com.admin.controller.base.BaseController;
import com.admin.entity.system.Menu;
import com.admin.entity.system.Role;
import com.admin.entity.system.User;
import com.admin.service.system.menu.MenuService;
import com.admin.service.system.role.RoleService;
import com.admin.service.system.user.UserService;
import com.admin.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 总入口
 */
@Controller
public class LoginController extends BaseController {

    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "menuService")
    private MenuService menuService;
    @Resource(name = "roleService")
    private RoleService roleService;


    /**
     * 获取登录用户的IP
     *
     * @throws Exception
     */
    public void getRemortIP(String USERNAME) throws Exception {
        PageData pd = new PageData();
        HttpServletRequest request = this.getRequest();
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        pd.put("USERNAME", USERNAME);
        pd.put("IP", ip);
        userService.saveIP(pd);
    }


    /**
     * 访问登录页
     *
     * @return
     */
    @RequestMapping(value = "/login_toLogin")
    public ModelAndView toLogin(HttpServletRequest request) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
        mv.setViewName("system/admin/login");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 请求登录，验证用户
     */
    @RequestMapping(value = "/login_login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String errInfo = "";
        String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq313596790fh", "").replaceAll("QQ978336446fh", "").split(",fh,");
        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
        //这些域名不需要验证码
        if(!(request.getRequestURL().toString().contains("test") || request.getRequestURL().toString().contains("uat") || request.getRequestURL().toString().contains("localhost"))){
            if (null != KEYDATA && KEYDATA.length == 3){
                String code = KEYDATA[2];
                if (null == code || "".equals(code)) {
                    errInfo = "nullcode"; //验证码为空
                }
                if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)) {
                    String USERNAME = KEYDATA[0];
                    String PASSWORD = KEYDATA[1];
                    pd.put("USERNAME", USERNAME);
                    String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();    //密码加密
                    pd.put("PASSWORD", passwd);
                    try {
                        pd = userService.getUserByNameAndPwd(pd);
                    } catch (Exception e) {
                        logger.info(e);
                    }
                    if (pd != null) {
                        if(pd.getString("IS_LOGOUT").equals("0")){//用户可用
                            pd.put("LAST_LOGIN", DateUtil.getTime().toString());
                            userService.updateLastLogin(pd);
                            User user = new User();
                            user.setUSER_ID((long)pd.get("USER_ID"));
                            user.setUSERNAME(pd.getString("USERNAME"));
                            user.setPASSWORD(pd.getString("PASSWORD"));
                            user.setNAME(pd.getString("NAME"));
                            user.setRIGHTS(pd.getString("RIGHTS"));
                            user.setROLE_ID((String)pd.get("ROLE_ID"));
                            user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
                            user.setIP(IPUtils.getRemoteAddr(request));
                            user.setSTATUS(pd.getString("STATUS"));
                            session.setAttribute(Const.SESSION_USER, user);
                            session.removeAttribute(Const.SESSION_SECURITY_CODE);
                            session.setAttribute("tel", pd.getString("PHONE"));
                            //shiro加入身份验证
                            Subject subject = SecurityUtils.getSubject();
                            UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
                            try {
                                subject.login(token);
                            } catch (AuthenticationException e) {
                                errInfo = "身份验证失败！";
                            }
                        }else{
                            errInfo = "islogout";                //已注销
                        }
                    } else {
                        errInfo = "usererror";                //用户名或密码有误
                    }
                }else{
                    errInfo = "codeerror";//验证码错误
                }
            }else{
                errInfo = "error";    //缺少参数
            }
        }else{
            String USERNAME = KEYDATA[0];
            String PASSWORD = KEYDATA[1];
            pd.put("USERNAME", USERNAME);
            String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();    //密码加密
            pd.put("PASSWORD", passwd);
            try {
                pd = userService.getUserByNameAndPwd(pd);
            } catch (Exception e) {
                logger.info(e);
            }
            if (pd != null) {
                if(pd.getString("IS_LOGOUT").equals("0")){//用户可用
                    pd.put("LAST_LOGIN", DateUtil.getTime().toString());
                    userService.updateLastLogin(pd);
                    User user = new User();
                    user.setUSER_ID((Long)pd.get("USER_ID"));
                    user.setUSERNAME(pd.getString("USERNAME"));
                    user.setPASSWORD(pd.getString("PASSWORD"));
                    user.setNAME(pd.getString("NAME"));
                    user.setRIGHTS(pd.getString("RIGHTS"));
                    user.setROLE_ID((String)pd.get("ROLE_ID"));
                    user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
                    user.setIP(IPUtils.getRemoteAddr(request));
                    user.setSTATUS(pd.getString("STATUS"));
                    session.setAttribute(Const.SESSION_USER, user);
                    session.removeAttribute(Const.SESSION_SECURITY_CODE);
                    session.setAttribute("tel", pd.getString("PHONE"));
                    //shiro加入身份验证
                    Subject subject = SecurityUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
                    try {
                        subject.login(token);
                    } catch (AuthenticationException e) {
                        errInfo = "身份验证失败！";
                    }
                }else{
                    errInfo = "islogout";                //已注销
                }
            } else {
                errInfo = "usererror";                //用户名或密码有误
            }
        }
        if (Tools.isEmpty(errInfo)) {
            errInfo = "success";                    //验证成功
        }
        map.put("result", errInfo);
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 访问系统首页
     */
    @RequestMapping(value = "/main/{changeMenu}")
    public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu) {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {

            //shiro管理的session
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();

            User user = (User) session.getAttribute(Const.SESSION_USER);
            if (user != null) {

                User userr = (User) session.getAttribute(Const.SESSION_USERROL);
                if (null == userr) {
                    Map map = new HashMap();
                    map.put("USER_ID",user.getUSER_ID());
                    //user = userService.getUserAndRoleById(map);
                    user = userService.getUserAndRoleByAll(map);
                    session.setAttribute(Const.SESSION_USERROL, user);
                } else {
                    user = userr;
                }
                String[] Role_ids = user.getROLE_ID().split(",");
                List<Role> roles = new ArrayList<Role>();
                List<String> roleRightss = new ArrayList<String>();
                for (String role_id : Role_ids) {
                    Role role = roleService.getRoleById(role_id);
                    String roleRight = role != null ? role.getRIGHTS() : "";
                    roles.add(role);
                    roleRightss.add(roleRight);
                }
//				Role role = user.getRole();
//				String roleRights = role!=null ? role.getRIGHTS() : "";
                //避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
//				session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); 		//将角色权限存入session
                session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRightss);        //将角色权限存入session
                session.setAttribute(Const.SESSION_USERNAME, user.getUSERNAME());    //放入用户名

                List<Menu> allmenuList = new ArrayList<Menu>();
                if (null == session.getAttribute(Const.SESSION_allmenuList)) {
                    allmenuList = menuService.listAllMenu();
                    for (String roleRights : roleRightss) {
                        if (Tools.notEmpty(roleRights)) {
                            for (Menu menu : allmenuList) {
                                if (!menu.isHasMenu()) {//判断是否已经有当前父菜单权限
                                    menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));//菜单权限判断
                                    if (menu.isHasMenu()) {
                                        List<Menu> subMenuList = menu.getSubMenu();
                                        for (Menu sub : subMenuList) {
                                            if (!sub.isHasMenu()) {
                                                sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
                                            }
                                        }
                                    }
                                } else {
                                    List<Menu> subMenuList = menu.getSubMenu();
                                    for (Menu sub : subMenuList) {
                                        if (!sub.isHasMenu()) {
                                            sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    session.setAttribute(Const.SESSION_allmenuList, allmenuList);            //菜单权限放入session中
                } else {
                    allmenuList = (List<Menu>) session.getAttribute(Const.SESSION_allmenuList);
                }
                //切换菜单=====
                /*List<Menu> menuList = new ArrayList<Menu>();
                //if(null == session.getAttribute(Const.SESSION_menuList) || ("yes".equals(pd.getString("changeMenu")))){
                if (null == session.getAttribute(Const.SESSION_menuList) || ("yes".equals(changeMenu))) {
                    List<Menu> menuList1 = new ArrayList<Menu>();
                    List<Menu> menuList2 = new ArrayList<Menu>();

                    //拆分菜单
                    for (int i = 0; i < allmenuList.size(); i++) {
                        Menu menu = allmenuList.get(i);
                        if ("1".equals(menu.getMENU_TYPE())) {
                            menuList1.add(menu);
                        } else {
                            menuList2.add(menu);
                        }
                    }

                    session.removeAttribute(Const.SESSION_menuList);
//					if("2".equals(session.getAttribute("changeMenu"))){
                    session.setAttribute(Const.SESSION_menuList, menuList1);
                    session.removeAttribute("changeMenu");
                    session.setAttribute("changeMenu", "1");
                    menuList = menuList1;
//					}else{
//						session.setAttribute(Const.SESSION_menuList, menuList2);
//						session.removeAttribute("changeMenu");
//						session.setAttribute("changeMenu", "2");
//						menuList = menuList2;
//					}
                } else {
                    menuList = (List<Menu>) session.getAttribute(Const.SESSION_menuList);
                }*/
                //切换菜单=====

                if (null == session.getAttribute(Const.SESSION_QX)) {
                    session.setAttribute(Const.SESSION_QX, this.getUQX(session));    //按钮权限放到session中
                }

                //FusionCharts 报表
                String strXML = "<graph caption='前12个月订单销量柱状图' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'><set name='2013-05' value='4' color='AFD8F8'/><set name='2013-04' value='0' color='AFD8F8'/><set name='2013-03' value='0' color='AFD8F8'/><set name='2013-02' value='0' color='AFD8F8'/><set name='2013-01' value='0' color='AFD8F8'/><set name='2012-01' value='0' color='AFD8F8'/><set name='2012-11' value='0' color='AFD8F8'/><set name='2012-10' value='0' color='AFD8F8'/><set name='2012-09' value='0' color='AFD8F8'/><set name='2012-08' value='0' color='AFD8F8'/><set name='2012-07' value='0' color='AFD8F8'/><set name='2012-06' value='0' color='AFD8F8'/></graph>";
                mv.addObject("strXML", strXML);
                //FusionCharts 报表

                mv.setViewName("system/admin/index");
                mv.addObject("user", user);
                //mv.addObject("menuList", menuList);
                mv.addObject("menuList", allmenuList);
            } else {
                mv.setViewName("system/admin/login");//session失效后跳转登录页面
            }


        } catch (Exception e) {
            mv.setViewName("system/admin/login");
            logger.error(e.getMessage(), e);
        }
        HttpServletRequest request = this.getRequest();
        String url = request.getRequestURL().toString();
        pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
        if (url.indexOf("sales") > 0) {
            pd.put("SYSNAME", "银票网CRM系统"); //读取系统名称
        }
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 进入tab标签
     *
     * @return
     */
    @RequestMapping(value = "/tab")
    public String tab() {
        return "system/admin/tab";
    }

    /**
     * 进入首页后的默认页面
     *
     * @return
     */
    @RequestMapping(value = "/login_default")
    public String defaultPage() {
        return "system/admin/default";
    }

    /**
     * 用户注销
     * //	 * @param session
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();

        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();

        session.removeAttribute(Const.SESSION_USER);
        session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
        session.removeAttribute(Const.SESSION_allmenuList);
        session.removeAttribute(Const.SESSION_menuList);
        session.removeAttribute(Const.SESSION_QX);
        session.removeAttribute(Const.SESSION_userpds);
        session.removeAttribute(Const.SESSION_USERNAME);
        session.removeAttribute(Const.SESSION_USERROL);
        session.removeAttribute("changeMenu");

        //shiro销毁登录
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        pd = this.getPageData();
        String msg = pd.getString("msg");
        pd.put("msg", msg);
        pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
        mv.setViewName("system/admin/login");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 获取用户权限
     */
    public Map<String, String> getUQX(Session session) {
        PageData pd = new PageData();
        Map<String, String> map = new HashMap<String, String>();
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        try {
            String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
            pd.put(Const.SESSION_USERNAME, USERNAME);
            String[] ROLE_IDs = userService.findByUId(pd).get("ROLE_ID").toString().split(",");
            for (String ROLE_ID : ROLE_IDs) {
                pd.put("ROLE_ID", ROLE_ID);

                PageData pd2 = new PageData();
                pd2.put(Const.SESSION_USERNAME, USERNAME);
                pd2.put("ROLE_ID", ROLE_ID);

                pd = roleService.findObjectById(pd);

                pd2 = roleService.findGLbyrid(pd2);
                if (null != pd2) {
                    map.put("FX_QX", pd2.get("FX_QX").toString());
                    map.put("FW_QX", pd2.get("FW_QX").toString());
                    map.put("QX1", pd2.get("QX1").toString());
                    map.put("QX2", pd2.get("QX2").toString());
                    map.put("QX3", pd2.get("QX3").toString());
                    map.put("QX4", pd2.get("QX4").toString());

                    pd2.put("ROLE_ID", ROLE_ID);
                    pd2 = roleService.findYHbyrid(pd2);
                    map.put("C1", pd2.get("C1").toString());
                    map.put("C2", pd2.get("C2").toString());
                    map.put("C3", pd2.get("C3").toString());
                    map.put("C4", pd2.get("C4").toString());
                    map.put("Q1", pd2.get("Q1").toString());
                    map.put("Q2", pd2.get("Q2").toString());
                    map.put("Q3", pd2.get("Q3").toString());
                    map.put("Q4", pd2.get("Q4").toString());
                }

                String adds = map.get("adds") == null ? "" : map.get("adds").toString() + ",";
                map.put("adds", adds + pd.getMyString("ADD_QX"));
                String dels = map.get("dels") == null ? "" : map.get("dels").toString() + ",";
                map.put("dels", dels + pd.getMyString("DEL_QX"));
                String edits = map.get("edits") == null ? "" : map.get("edits").toString() + ",";
                map.put("edits", edits + pd.getMyString("EDIT_QX"));
                String chas = map.get("chas") == null ? "" : map.get("chas").toString() + ",";
                map.put("chas", chas + pd.getMyString("CHA_QX"));
            }
            //System.out.println(map);

            this.getRemortIP(USERNAME);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return map;
    }

}
