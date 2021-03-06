package com.example.blt.controller;

import com.example.blt.entity.office.OfficePa;
import com.example.blt.entity.office.OfficeWS;
import com.example.blt.service.TpadOfficeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: central-console
 * @description: 天平办公室
 * @author: Mr.Ma
 * @create: 2019-09-30 10:53
 **/
@RestController
@RequestMapping("/office")
@Validated
public class TpadOfficeController {

    @Resource
    private TpadOfficeService tpadOfficeService;

    Logger logger = LoggerFactory.getLogger(TpadOfficeController.class);

    public TpadOfficeController(TpadOfficeService tpadOfficeService) {
        this.tpadOfficeService = tpadOfficeService;
    }



    @PostMapping("/get")
    public Map<String, Object> get(@Valid @RequestBody OfficePa office) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        String project = office.getProject();
        Map<String,Object> parameterSetting;
        Map<String,Object> parameterSettings = new HashMap<>();
        try {
            //获取基础配置(基础单元类型，场景个数，当前场景，x，y)
            parameterSetting = tpadOfficeService.getParameterSetting(project);
            //返回初始化数据
            parameterSettings = tpadOfficeService.getUnits(parameterSetting);
        } catch (Exception e) {
            Object[] errorArr = {this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage()};
            logger.error("controller:[{}],method:[{}],result:[{}]",errorArr);
        }
        map.put("data",parameterSettings);
        return map;
    }

    /**
     * @param office
     * @return
     */
    @PostMapping("/sendCmd")
    public String sendCmd(@RequestBody OfficePa office) {
        String project = office.getProject();
        String status = "success";
        try {
//            long start = System.currentTimeMillis();
            //获取基础配置(基础单元类型，场景个数，当前场景，x，y)
            Map<String,Object> parameterSetting = tpadOfficeService.getParameterSetting(project);
            String unit = (String) parameterSetting.get("unit");
            tpadOfficeService.send(unit,office);
//            long end = System.currentTimeMillis();
//            logger.warn("send time:{}",end-start);
        } catch (Exception e) {
            status = "error";
            status = e.getMessage();
            logger.error("method: sendCmd;result {}",e.getMessage());
        }
        return status;
    }

    /**
     * 解析websocket
     * @param officeWS
     * @return
     */
    @PostMapping("/analysisWs")
    public Map<String,Integer> analysisWs(@RequestBody OfficeWS officeWS){
        String project = officeWS.getProject();
        Map<String, Integer> map = new HashMap<>();
        Integer statue = 0;
        try {
            //获取基础配置(基础单元类型，场景个数，当前场景，x，y)
            Map<String,Object> parameterSetting = tpadOfficeService.getParameterSetting(project);
            map = tpadOfficeService.analysisWsAndStorageStatus(parameterSetting,officeWS);
            map.put("success",statue);
        } catch (Exception e) {
            logger.error("method: analysisWs;result {}",e.getMessage());
            statue = 1;
            map.put("success",statue);
        }
        return map;
    }
}
