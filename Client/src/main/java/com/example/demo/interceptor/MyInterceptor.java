package com.example.demo.interceptor;

import com.example.demo.util.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-02-11.
 */
public class MyInterceptor  implements HandlerInterceptor {

    private Logger logger = LogManager.getLogger(getClass());
    private SessionUtil<Map> sessionUtil = new SessionUtil<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        String url = request.getRequestURI();// 得到请求URL
        url = url.replace(request.getContextPath(), "");
        if(url.startsWith("/resources/")) { //不拦截的资源
            return true;
        }

        if(isUserLogined()) {//已经登录
            return true;
        }
        else {
            //没有登录
            sendRedirectTo(request, response, "timeout", "/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

    private boolean isUserLogined() {
        //FIXME 需要将此处改为用户登录的限制即可
        return sessionUtil.getLoginUser() != null;
    }

    private void sendRedirectTo(HttpServletRequest request, HttpServletResponse response, String type, String url) {
        try {
            sessionUtil.logout();
            String httpTag = request.getHeader("Request-By");
            if(httpTag != null && httpTag.equals("AgHttp")) {
                Map<String, Object> obj = new HashMap<String, Object>();
                obj.put("success", true);
                obj.put("result", type);
                obj.put("info","登录超时或者权限被收回！请重新登录！");
                obj.put("redirectURL", request.getContextPath() + url);
            }
            else {
                response.sendRedirect(request.getContextPath() + url);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
