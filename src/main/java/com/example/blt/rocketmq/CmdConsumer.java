package com.example.blt.rocketmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author hongjian.chen
 * @date 2019/6/25 16:26
 */

@Slf4j
@Service
@RocketMQMessageListener(topic = "blt_remote_console_topic", consumerGroup = "my-consumer_cmd_group")
public class CmdConsumer implements RocketMQListener<String> {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(String message) {
        try {
            Map map = JSON.parseObject(message);
            sqlSessionTemplate.selectOne("console.saveConsole", map);
            logger.info("result=" + map.get("result"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
