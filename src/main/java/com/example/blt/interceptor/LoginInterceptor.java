package com.example.blt.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-04 10:46
 **/
public class LoginInterceptor implements HandlerInterceptor {

    private List<String> url = new ArrayList();
    /**
     * 开始进入地址请求拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("username") != null){
            return true;
        }else{
            response.sendRedirect("/control/login");	//未登录，跳转到登录页
            return false;
        }
    }

    /**
     * 定义排除拦截URL
     * @return
     */
//    public List<String> getUrl(){
//        url.add("/control/login");      //登录页
//        url.add("/control/toLogin");   //登录action URL
//
//        //网站静态资源
//        url.add("/");
//        url.add("/index");
//        url.add("/myIndex");
//        url.add("/welcome");
//        url.add("/index2");
//        url.add("/newIndex");
//        url.add("/highway");
//        url.add("/newIndex/noEnergy");
//        url.add("/get");
//        url.add("/showHost");
//        url.add("/refreshHosts");
//        url.add("/getHost");
//        url.add("/save");
//        url.add("/uploadDataFromAlink");
//        url.add("/getMsgByMF2");
//        url.add("/getMsgByMF");
//        url.add("/sendSocket7");
//        url.add("/sendSocket6");
//        url.add("/sendSocket5");
//        url.add("/sendSocket4");
//        url.add("/test");
//        url.add("/switch");
//        url.add("/monitor");
//        url.add("/monitor2");
//        url.add("/monitor3");
//        url.add("/getMonitor2");
//        url.add("/getMonitor3");
//        url.add("/getNewMonitor");
//        url.add("/getNewMonitorLightStatus");
//        url.add("/getLightOnOrOff");
//        url.add("/getMonitor2LightStatus");
//        url.add("/getMonitorLightStatus");
//        url.add("/getNewMonitor");
//        url.add("/getLeft");
//        return url;
//    }
}
