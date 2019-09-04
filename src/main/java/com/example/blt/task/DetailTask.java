package com.example.blt.task;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.Groups;
import com.example.blt.entity.dd.Scenes;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.netty.ClientMain;
import com.example.blt.utils.SpringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hongjian.chen
 * @date 2019/8/19 11:25
 */

public class DetailTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    private CronVo cronVo;

    public DetailTask(CronVo cronVo) {
        this.cronVo = cronVo;
    }

    @Override
    public void run() {
        String hostId = sqlSessionTemplate.selectOne("console.getHostId", cronVo.getMeshId());
        int sceneId = cronVo.getSceneId();
        JSONObject object = new JSONObject();
        object.put("host", hostId);
        switch (sceneId) {
            case 1:
                object.put("command", Scenes.SCENES1.getScode());
                break;
            case 2:
                object.put("command", Scenes.SCENES2.getScode());
                break;
            case 3:
                object.put("command", Scenes.SCENES3.getScode());
                break;
            case 4:
                object.put("command", Scenes.SCENES4.getScode());
                break;
            case 5:
                object.put("command", Scenes.SCENES5.getScode());
                break;
            case 6:
                object.put("command", Scenes.SCENES6.getScode());
                break;
            case 7:
                object.put("command", Scenes.SCENES7.getScode());
                break;
            case 8:
                object.put("command", Scenes.SCENES8.getScode());
                break;
            case 21:
                object.put("command", Groups.GROUPSA.getOff());
                break;
            case 22:
                object.put("command", Groups.GROUPSA.getOn());
                break;
        }
        ClientMain.sendCron(object.toJSONString());
        logger.warn("cmdInfo", object.toJSONString());
    }
}
