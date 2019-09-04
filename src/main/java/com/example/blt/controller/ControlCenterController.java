package com.example.blt.controller;

import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.control.ControlMesh;
import com.example.blt.entity.control.GroupList;
import com.example.blt.entity.control.MeshList;
import com.example.blt.service.ControlCenterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "poeConsole/login";
    }

    /**
     * 点击登录
     * @param request session
     * @param username 用户名
     * @return
     */
    @RequestMapping("/toLogin")
    @ResponseBody
    public String toLogin(HttpServletRequest request,String username){
        HttpSession session = request.getSession();
        session.setAttribute("username",username);
        return "success";
    }

    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        return "redirect:login";
    }

    /**
     * 跳转到index.html
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "poeConsole/index";
    }

    /**
     * 跳转到timing.html
     * @param model
     * @return
     */
    @RequestMapping("/timer")
    public String timer(Model model,String meshId) {
        List<MeshList> meshs = controlCenterService.getMeshs();
        if (StringUtils.isBlank(meshId) && meshs.size()>0){
            meshId = meshs.get(0).getMeshId();
        }
        if (StringUtils.isNotBlank(meshId)){
            List<TimeLine> timeLines = controlCenterService.getTimeLinesByMeshId(meshId);
            if (timeLines.size()>0){
                Integer tsid = timeLines.get(0).getId();
                List<TimePoint> timePoints = controlCenterService.getTimePointByTsid(tsid);
                model.addAttribute("timeLines",timeLines);
                model.addAttribute("timePoints",timePoints);
            }
        }

        for (MeshList mesh:meshs){
            if (mesh.getMeshId().equals(meshId)){
                model.addAttribute("mname",mesh.getMname());
            }
        }
        model.addAttribute("meshs",meshs);
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
        List<ControlMesh> controlMeshs = controlCenterService.getControlGroups();
        List<GroupList> groupList = controlCenterService.getGroups();
        model.addAttribute("groupList",groupList);//组列表
        model.addAttribute("controlMeshs",controlMeshs);//组列表
        return "poeConsole/device";
    }

    /**
     * 创建/重命名/删除组
     * @param gname 组名
     * @param type 操作类型
     * @param id 组id
     * @return  exitGroup:1 名称重复
     */
    @RequestMapping("/group")
    @ResponseBody
    public Map<String,Object> group(String gname,String type,Integer id){
        Map<String,Object> groupMap = new HashMap<>();
        Boolean flag = controlCenterService.groupOperation(gname,type,id);
        if (!flag){//组名重复
            groupMap.put("exitGroup",1);
        }else {
            groupMap.put("exitGroup",0);
        }
        return groupMap;
    }

    /**
     * 重命名网络名
     * @param mname 网络名
     * @param meshId 网络id
     * @return exitMname:1 名称重复
     */
    @RequestMapping("/renameMesh")
    @ResponseBody
    public Map<String,Object> renameMesh(String mname,String meshId){
        Map<String,Object> meshMap = new HashMap<>();
        Integer count = controlCenterService.getMname(mname);
        if (count > 0){
            meshMap.put("exitMname",1);
        }else {
            meshMap.put("exitMname",0);
            controlCenterService.renameMesh(mname,meshId);
        }
        return meshMap;
    }

    /**
     * 重命名/删除面板
     * @param mac
     * @param pname
     * @param type
     * @return
     */
    @RequestMapping("/panelOperations")
    @ResponseBody
    public Map<String,Object> panelOperations(String mac,String pname,String type){
        Map<String,Object> panelMap = new HashMap<>();
        Boolean flag = controlCenterService.panelOperations(mac,pname,type);
        if (!flag){//名称重复
            panelMap.put("exitPname",1);
        }else {
            panelMap.put("exitPname",0);
        }
        return panelMap;
    }

    /**
     * 设置主控关系
     * @param meshId 网络id
     * @return
     */
    @RequestMapping("/setMaster")
    @ResponseBody
    public Map<String,Object> setMaster(String meshId){
        Map<String,Object> masterMap = new HashMap<>();
        controlCenterService.updateMaster(meshId);
        masterMap.put("success","success");
        return masterMap;
    }

}
