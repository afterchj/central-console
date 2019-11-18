package com.example.blt.service;

import com.example.blt.dao.ParameterSettingDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-18 10:51
 **/
@Service
public class ParameterSettingService {

    @Resource
    private ParameterSettingDao parameterSettingDao;

    public Integer getParameterSettingCount(){
        return parameterSettingDao.getParameterSettingCount();
    }

    public void saveParameterSetting(String project, int sceneCount, String unit){
        parameterSettingDao.saveParameterSetting(project, sceneCount, unit);
    }

}
