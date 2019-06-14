//package com.example.blt.utils;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import javax.persistence.EntityManagerFactory;
//
///**
// * Created by hongjian.chen on 2019/6/13.
// */
//public class SpringJpaUtil {
//
//    private static ApplicationContext ctx = null;
//
//    static {
//        ctx = new ClassPathXmlApplicationContext("classpath:spring-hibernate.xml");
//    }
//
//    public static EntityManagerFactory getEntityManager() {
//        return (EntityManagerFactory) ctx.getBean("entityManagerFactory");
//    }
//
//    public static Object getBean(Class clazz) {
//        return ctx.getBean(clazz);
//    }
//}
