package com.example.blt.controller;

import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.service.ControlCenterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description: 定时功能
 * @author: Mr.Ma
 * @create: 2019-09-03 13:13
 **/
@Controller
@RequestMapping("/control")
public class ControlCenterController {

    @Resource
    private ControlCenterService controlCenterService;

    /**
     * 跳转到login.html
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "poeConsole/login";
    }

    /**
     * 点击登录 跳转到index.html
     * @param model
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(Model model, String username, String password) {
//        if (username.equals("admin") && password.equals("admin")){
//            return "poeConsole/index";
//        }else {
        return "poeConsole/index";
//        }
    }

    /**
     * 跳转到index.html
     *
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        return "poeConsole/index";
    }

    /**
     * 跳转到timing.html
     * @param model
     * @return
     */
    @RequestMapping("/timer")
    public String timer(Model model) {
        String meshId = "88888888";
        List<TimeLine> timeLines = controlCenterService.getTimeLinesByMeshId(meshId);
        Integer tsid = timeLines.get(0).getId();
        List<TimePoint> timePoints = controlCenterService.getTimePointByTsid(tsid);
        model.addAttribute("timeLines",timeLines);
        model.addAttribute("timePoints",timePoints);
        return "poeConsole/timing";
    }

    @RequestMapping("/timePointList")
    @ResponseBody
    public Map timePointList(Integer tsid){
        Map map = new HashMap();
        List<TimePoint> timePoints = controlCenterService.getTimePointByTsid(tsid);
        map.put("timePoints",timePoints);
        return map;
    }

    /**
     * 跳转到device.html
     *
     * @param model
     * @return
     */
    @RequestMapping("/netWorkGroupConsole")
    public String netWorkGroupConsole(Model model) {
        return "poeConsole/device";
    }
}
