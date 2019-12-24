package com.example.blt;

import com.example.blt.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * Created by hongjian.chen on 2019/5/17.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                .allowedHeaders("*")
                .allowedMethods("*")
                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                .exposedHeaders("Header1", "Header2");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/old").setViewName("oldIndex");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        String[] url = {"/central-console/control/index", "/central-console/control/timer", "/central-console/control/netWorkGroupConsole"};
        String[] url = {"/control/index", "/control/timer", "/control/netWorkGroupConsole"};
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(url);
//        registry.addInterceptor(protectSameCommitInterceptor()).addPathPatterns("/office/sendCmd");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    //提前注入bean，否则拦截器中注解无效
//    @Bean
//    public ProtectSameCommitInterceptor protectSameCommitInterceptor(){
//        return new ProtectSameCommitInterceptor();
//    }

}
