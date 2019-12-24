//package com.example.blt.filter;
//
//import com.example.blt.utils.CustomRequestWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
///**
// * @program: central-console
// * @description:
// * @author: Mr.Ma
// * @create: 2019-12-17 17:02
// **/
//@Slf4j
//@Component
//@WebFilter(urlPatterns = {"/office/sendCmd"})
//public class CustomFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        log.info(">>>> customFilter init <<<<");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        log.info(">>>> customFilter doFilter start <<<<");
//        CustomRequestWrapper requestWapper = null;
//        if (servletRequest instanceof HttpServletRequest) {
//            requestWapper = new CustomRequestWrapper((HttpServletRequest) servletRequest);
//        }
//
//        if (requestWapper != null) {
//            filterChain.doFilter(requestWapper,servletResponse);
//        } else {
//            filterChain.doFilter(servletRequest,servletResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {
//        log.info(">>>> customFilter destroy <<<<");
//    }
//}
//
