package com.admin.integration;

//import cn.vcredit.jd.emergencymoney.entity.User;
import com.admin.exception.ValidateException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller基类
 */

public class BaseController{
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String FAIL = "fail";
	private static final String SESSION_USER_KEY = "user";
	
	

	/**
	 * 判断字符串是否为空
	 * @param value
	 * @return
	 */
	protected boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
	
	/**
	 * 判断List是否为空
	 * @param list
	 * @return
	 */
	protected boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	/**
	 * 从Map获取值，如果NULL则返回空串
	 * @param map
	 * @param key
	 * @return
	 */
	protected Object getValue(Map<String, Object> map, String key) {
		Object obj = map.get(key);
		return obj == null ? "" : obj;
	}
	
	/**
	 * 设置session值
	 * @param request
	 * @param name 会话KEY
	 * @param value 对象值
	 */
	protected void setSessionAttribute(HttpServletRequest request, String name, Object value) {
		request.getSession().setAttribute(name, value);
	}
	
	/**
	 * 获取session值
	 * @param request
	 * @param name 会话KEY
	 * @return 对象值
	 */
	protected Object getSessionAttribute(HttpServletRequest request, String name) {
		return request.getSession().getAttribute(name);
	}
	
	/**
	 * 设置COOKIE
	 * @param cookies
	 * @param response
	 * @param time
	 */
	protected void addCookies(List<Cookie> cookies,HttpServletResponse response,String domain,int time){
		for(Cookie cookie:cookies){
			cookie.setDomain(domain);
			cookie.setMaxAge(time);            
			cookie.setPath("/");   
			response.addCookie(cookie);
		}
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,new PropertyEditorSupport(){
        	@Override
        	public void setAsText(String text) throws IllegalArgumentException {
        		if(text!=null&&text.trim().length()!=0)
        			setValue(new Date(text.replace("T", " ").replaceAll("-", "/")));
        	}
        });
//        binder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat, true));
    }
	protected void throwValidateException(String message) throws ValidateException {
		throw new ValidateException(message);
	}
	
	protected void validateEmpty(String value, String message) throws ValidateException {
		if (isEmpty(value)) throwValidateException(message);
	}

	protected void validateEmpty(List<?> list, String message) throws ValidateException {
		if (isEmpty(list)) throwValidateException(message);
	}

	protected void validateEqual(String value, String otherValue, String message) throws ValidateException {
		if (value != null && !value.equals(otherValue)) throwValidateException(message);
	}
	
	protected String getRealPath(HttpServletRequest request, String path) {
		return request.getSession().getServletContext().getRealPath(path);
	}
//	protected User getUser(HttpSession session) {
//		return (User)session.getAttribute(SESSION_USER_KEY);
//	}
//
//	protected User getUser(HttpServletRequest request) {
//		return getUser(request.getSession());
//	}
	
	
}