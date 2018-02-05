package com.example.demo.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class MainSessionUtil {

    private static final String MAIN_SESSION_LOGIN_INFO = "SessionUser";
    /**
     * SessionUser  后台用户信息注册
     * @param userInfo
     */
	public static void login(Map userInfo) {
        getSession().setAttribute(MAIN_SESSION_LOGIN_INFO,userInfo);
	}

    /**
     * 用户信息登出
     */
	public static void logout() {
		getSession().removeAttribute(MAIN_SESSION_LOGIN_INFO);
		getSession().invalidate();
	}

    /**
     * 返回Session中的用户信息名
     * @return
     */
	public static Map getLoginUser() {
        Object sessionUser = getSession().getAttribute(MAIN_SESSION_LOGIN_INFO);
		if(sessionUser!=null){
            Map userInfo =(Map)sessionUser;
            return userInfo;
		}
		return null;
	}
    /**
     *
     * @param name  菜单名称  route menus
     * @return  菜单列表或样式
     */
    public static List getLoginMenu(String name) {
        Object sessionUser = getSession().getAttribute(MAIN_SESSION_LOGIN_INFO);
        if(sessionUser!=null){
            Map userInfo =(Map)sessionUser;
            return (List) userInfo;
        }
        return null;
    }
	
	public static HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession();
	}
}
