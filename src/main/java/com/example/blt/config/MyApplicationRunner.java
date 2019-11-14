package com.example.blt.config;

import com.example.blt.dao.ControlCenterDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;

/**
 * @program: central-console
 * @description: 默认创建三个组
 * @author: Mr.Ma
 * @create: 2019-10-09 11:36
 **/
@Component
public class MyApplicationRunner implements ApplicationRunner,Order {

    @Resource
    private ControlCenterDao controlCenterDao;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Integer count = controlCenterDao.getGroupCount();
        String gname;
        StringBuffer sb;
        if (count == 0){
            for (int i=1;i<4;i++){
                sb = new StringBuffer();
                gname = sb.append("组").append(i).toString();
                controlCenterDao.createGroup(gname);
            }
        }
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
