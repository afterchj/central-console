package com.example.blt.controller;

import com.alibaba.fastjson.JSON;
import com.example.blt.entity.MPlace;
import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.control.ControlHost;
import com.example.blt.entity.control.ControlMaster;
import com.example.blt.entity.control.GroupList;
import com.example.blt.entity.control.MeshList;
import com.example.blt.entity.office.CmdJoin;
import com.example.blt.service.ControlCenterService;
import com.example.blt.service.TpadOfficeService;
import com.example.blt.socket.EchoClient;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: central-console
 * @description: 定时功能
 * @author: Mr.Ma
 * @create: 2019-09-03 13:13
 **/
@Controller
@RequestMapping("/control")
public class ControlCenterController {

    private static final EchoClient CLIENT_MAIN = new EchoClient();

    @Resource
    private ControlCenterService controlCenterService;

    @Resource
    private TpadOfficeService tpadOfficeService;

    Logger logger = LoggerFactory.getLogger(ControlCenterController.class);

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
     * 点击登录
     *
     * @param request  session
     * @param username 用户名
     * @return
     */
    @RequestMapping("/toLogin")
    @ResponseBody
    public String toLogin(HttpServletRequest request, String username, String pwd) {
        String status = "success";
        if (!"admin".equals(username) || !"admin".equals(pwd)) {
            status = "error";
            return status;
        }
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        return status;
    }

    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        return "redirect:login";
    }

    /**
     * 跳转到index.html
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "poeConsole/index";
    }

    /**
     * 跳转到timing.html
     *
     * @param model
     * @return
     */
    @RequestMapping("/timer")
    public String timer(Model model, String meshId) {
        List<MeshList> meshs = controlCenterService.getMeshs();
        if (StringUtils.isBlank(meshId) && meshs.size() > 0) {
            meshId = meshs.get(0).getMeshId();
        }
        if (StringUtils.isNotBlank(meshId)) {
            List<TimeLine> timeLines = controlCenterService.getTimeLinesByMeshId(meshId);
            if (timeLines.size() > 0) {
                Integer tsid = timeLines.get(0).getId();
                List<TimePoint> timePoints = controlCenterService.getTimePointByTsid(tsid);
                model.addAttribute("timeLines", timeLines);
                model.addAttribute("timePoints", timePoints);
            }
        }
        for (MeshList mesh : meshs) {
            if (mesh.getMeshId().equals(meshId)) {
                model.addAttribute("mname", mesh.getMname());
            }
        }
        model.addAttribute("meshs", meshs);
        return "poeConsole/timing";
    }

    @RequestMapping("/timePointList")
    @ResponseBody
    public Map timePointList(Integer tsid) {
        Map map = new HashMap();
        List<TimePoint> timePoints = controlCenterService.getTimePointByTsid(tsid);
        map.put("timePoints", timePoints);
        return map;
    }

    /**
     * 跳转到 control.html
     *
     * @param model
     * @return
     */
    @RequestMapping("/netWorkGroupConsole")
    public String netWorkGroupConsole(Model model, Integer gid, String meshId) {
        List<ControlMaster> controlMasters = controlCenterService.getControlGroups(gid, meshId);
        List<GroupList> groupList = controlCenterService.getGroups();
        List<MPlace> placeList = controlCenterService.getPlaces();
        model.addAttribute("groupList", groupList);//组列表
        model.addAttribute("controlMasters", controlMasters);//网络列表
        model.addAttribute("placeList", placeList);//区域列表
        return "poeConsole/control";
    }

    @RequestMapping("/getPanels")
    @ResponseBody
    public Map<String, Object> getPanels(String meshId) {
        Map<String, Object> panelMap = new HashMap<>();
        List<ControlHost> controlHosts = controlCenterService.getPanels(meshId);
        if (controlHosts.size() > 0) {
            ControlHost meshAndPOEStatus = controlCenterService.getMeshAndPOEStatus(controlHosts.get(0), meshId);
            panelMap.put("meshAndPOEStatus", meshAndPOEStatus);
        }
        panelMap.put("controlHosts", controlHosts);
        panelMap.put("meshId", meshId);
        return panelMap;
    }

    /**
     * 创建/重命名/删除组/选择组
     *
     * @param gname 组名
     * @param type  操作类型
     * @param id    组id
     * @return exitGroup:1 名称重复
     */
    @RequestMapping("/group")
    @ResponseBody
    public Map<String, Object> group(String type, Integer id, String gname) {
        Map<String, Object> groupMap = new HashMap<>();
        Boolean flag = controlCenterService.groupOperation(gname, type, id);
        //组名重复 exitGroup:1
        int exitGroup = (!flag) ? 1 : 0;
        groupMap.put("exitGroup", exitGroup);
        return groupMap;
    }

    /**
     * 重命名/删除网络名
     *
     * @param mname  网络名
     * @param meshId 网络id
     * @return exitMname:1 名称重复
     */
    @RequestMapping("/renameMesh")
    @ResponseBody
    public Map<String, Object> renameMesh(String mname, String meshId) {
        Map<String, Object> meshMap = new HashMap<>();
        Boolean flag = controlCenterService.meshOperations(mname, meshId);
        int exitMname = (flag) ? 0 : 1;
        meshMap.put("exitMname", exitMname);
        return meshMap;
    }

    /**
     * 重命名/删除面板
     *
     * @param id
     * @param pname
     * @param type
     * @return
     */
    @RequestMapping("/panelOperations")
    @ResponseBody
    public Map<String, Object> panelOperations(int id, String pname, String type) {
        Map<String, Object> panelMap = new HashMap<>();
        Boolean flag = controlCenterService.panelOperations(id, pname, type);
        if (!flag) {//名称重复
            panelMap.put("exitPname", 1);
        } else {
            panelMap.put("exitPname", 0);
        }
        return panelMap;
    }

    /**
     * 设置主控关系
     *
     * @param meshId 网络id
     * @return
     */
    @RequestMapping("/setMaster")
    @ResponseBody
    public Map<String, Object> setMaster(String meshId, int type) {
        Map<String, Object> masterMap = new HashMap<>();
        controlCenterService.updateMaster(meshId, type);
        masterMap.put("success", "success");
        return masterMap;
    }

    @RequestMapping("/reSet")
    @ResponseBody
    public String reSet(String type) {
        String msg = "success";
        String host = "all";
        if (type.equals("reSet")) {
            controlCenterService.reSet();
        } else {
            String[] cmdArr = {CmdJoin.CMD_INQUIRY_MESHID.getKey(), CmdJoin.CMD_INQUIRY_MAC.getKey(), CmdJoin.CMD_INQUIRY_VERSION.getKey()};
//            try {

//                tpadOfficeService.send(host,cmdArr[0]);
//                tpadOfficeService.send(host,cmdArr[1]);
//                tpadOfficeService.send(host,cmdArr[2]);
            send(host, cmdArr[0]);
            logger.warn("method:[reSet] host:[all] cmd:[{}]", cmdArr[0]);
            send(host, cmdArr[1]);
            logger.warn("method:[reSet] host:[all] cmd:[{}]", cmdArr[1]);
            send(host, cmdArr[2]);
            logger.warn("method:[reSet] host:[all] cmd:[{}]", cmdArr[2]);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                recursiveSendCmd(host,cmdArr);
//            } catch (Exception e) {
//                msg = "error";
//            }
        }
        return msg;
    }

    public void send(String hostId, String cmd) {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("command", cmd);
        map.put("host", hostId);
        ControlTask task = new ControlTask(CLIENT_MAIN, JSON.toJSONString(map));
        ExecuteTask.sendCmd(task);
    }

    private void recursiveSendCmd(String host, String... cmdArr) throws Exception {
        for (int i = 0; i < cmdArr.length; i++) {
            tpadOfficeService.send(host, cmdArr[i]);
        }
    }
}
