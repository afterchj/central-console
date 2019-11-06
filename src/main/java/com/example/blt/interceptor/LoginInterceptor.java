package com.example.blt.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-04 10:46
 **/
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 开始进入地址请求拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean flag = true;
        HttpSession session = request.getSession();
        if(session.getAttribute("username") == null){
            response.sendRedirect("/central-console/control/login");//未登录，跳转到登录页
            flag = false;
        }
        return flag;
    }
}
