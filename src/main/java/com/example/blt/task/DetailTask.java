package com.example.blt.task;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.Groups;
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
        StringBuilder cmd = new StringBuilder("77010219");
        int sceneId = cronVo.getSceneId();
        JSONObject object = new JSONObject();
        object.put("host", hostId);
        switch (sceneId) {
            case 21:
                object.put("command", Groups.GROUPSA.getOff());
                break;
            case 22:
                object.put("command", Groups.GROUPSA.getOn());
                break;
            default:
                String strHex = Integer.toHexString(sceneId).toUpperCase();
                if (strHex.length() == 1) {
                    cmd.append("0" + sceneId);
                } else {
                    cmd.append(sceneId);
                }
                object.put("command", cmd.toString());
                break;
        }
        ClientMain.sendCron(object.toJSONString());
    }
}
