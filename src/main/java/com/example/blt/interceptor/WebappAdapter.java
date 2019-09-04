//package com.example.blt.interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @program: central-console
// * @description:
// * @author: Mr.Ma
// * @create: 2019-09-04 10:56
// **/
//@Configuration
//public class WebappAdapter implements WebMvcConfigurer {
//    /**
//     * 注册拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        LoginInterceptor loginInterceptor = new LoginInterceptor();
//        // addPathPatterns 添加拦截url，     excludePathPatterns 排除拦截url
////        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(loginInterceptor.getUrl());
//        String[] url = {"/control/index","/control/timer","/control/netWorkGroupConsole"};
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(url);
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
//}
