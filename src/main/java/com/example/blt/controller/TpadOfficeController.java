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

//    @RequestMapping("/index")
//    public String index(){
//        return "redirect:../dist/index.html";
//    }

    @PostMapping("/get")
    public Map<String, Object> get(@Valid @RequestBody OfficePa office) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        String project = office.getProject();
        Map<String,Object> parameterSetting;
        Map<String,Object> parameterSettings = new HashMap<>();
        try {
            parameterSetting = tpadOfficeService.getParameterSetting(project);
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
            Map<String,Object> parameterSetting = tpadOfficeService.getParameterSetting(project);
            String unit = (String) parameterSetting.get("unit");
            tpadOfficeService.send(unit,office);
        } catch (Exception e) {
            status = "error";
            logger.error("method: sendCmd;result {}",e.getMessage());
        }
        return status;
    }

    @PostMapping("/analysisWs")
    public Map<String,Integer> analysisWs(@RequestBody OfficeWS officeWS){
        String project = officeWS.getProject();
        Map<String, Integer> map = new HashMap<>();
        try {
            Map<String,Object> parameterSetting = tpadOfficeService.getParameterSetting(project);
            map = tpadOfficeService.analysisWsAndStorageStatus(parameterSetting,officeWS);
        } catch (Exception e) {
            logger.error("method: analysisWs;result {}",e.getMessage());
        }
        return map;
    }
}
