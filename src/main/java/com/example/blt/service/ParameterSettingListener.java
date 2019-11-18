package com.example.blt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-18 11:03
 **/
@Service
public class ParameterSettingListener implements ApplicationListener {

    Logger logger = LoggerFactory.getLogger(ParameterSettingListener.class);

    @Resource
    private ParameterSettingService parameterSettingService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Integer count = parameterSettingService.getParameterSettingCount();
        if (count <= 0){
            String project = "tpadOffice";
            int sceneCount = 10;
            String unit = "space";
            logger.warn("start init table t_parameter_setting ....");
            parameterSettingService.saveParameterSetting(project, sceneCount, unit);
            logger.warn("init table t_parameter_setting success; project:{},sceneCount:{},unit:{}",project,sceneCount,unit);
        }
    }
}
