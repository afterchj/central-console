package com.example.blt.task;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.netty.ClientMain;
import com.example.blt.socket.EchoClient;
import com.example.blt.utils.SpringUtils;
import org.apache.commons.lang.StringUtils;
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
    private static final EchoClient CLIENT_MAIN = new EchoClient();
    private CronVo cronVo;

    public DetailTask(CronVo cronVo) {
        this.cronVo = cronVo;
    }

    @Override
    public void run() {
//        logger.warn("网络id [{}] 任务名称 [{}] 执行命令 [{}]",cronVo.getMeshId(),cronVo.getCronName(),cronVo.getCommand());
        String hostId = sqlSessionTemplate.selectOne("console.getHostId", cronVo.getMeshId());
        if (StringUtils.isNotEmpty(hostId)) {
            JSONObject object = new JSONObject();
            object.put("host", hostId);
            object.put("command", cronVo.getCommand());
            CLIENT_MAIN.sendCron(object.toJSONString());
        }
    }
}
