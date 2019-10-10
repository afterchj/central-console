package com.example.blt.controller;

import com.example.blt.entity.office.OfficePa;
import com.example.blt.service.TpadOfficeService;
import com.example.blt.utils.DimmingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: central-console
 * @description: 天平办公室
 * @author: Mr.Ma
 * @create: 2019-09-30 10:53
 **/
@Controller
@RequestMapping("/office")
public class TpadOfficeController {

    static Map<String, String> colors;
    static Map<String, String> luminances;

    static {
        colors = DimmingUtil.toAddHexList2("colors");
        luminances = DimmingUtil.toAddHexList2("luminances");
    }

    @Resource
    private TpadOfficeService tpadOfficeService;

    @PostMapping("/get")
    @ResponseBody
    public Map<String, Object> get(@RequestBody OfficePa office) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        String project = office.getProject();
        Map<String,Object> parameterSetting = tpadOfficeService.getParameterSetting(project);
        Map<String,Object> parameterSettings = tpadOfficeService.getUnits(parameterSetting);
        map.put("data",parameterSettings);
        return map;
    }

    /**
     * @param office
     * @return
     */
    @PostMapping("/sendCmd")
    @ResponseBody
    public String sendCmd(@RequestBody OfficePa office) {
        String project = office.getProject();
        Map<String,Object> parameterSetting = tpadOfficeService.getParameterSetting(project);
        String unit = (String) parameterSetting.get("unit");
        try {
            tpadOfficeService.send(unit,office);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

}
