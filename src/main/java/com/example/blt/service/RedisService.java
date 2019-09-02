package com.example.blt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hongjian.chen
 * @date 2019/9/2 13:39
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void pushMsg(String msg) {
        redisTemplate.convertAndSend("channel_cron", msg);
    }

    public void consumeMsg(String msg) { //具体操作方法 名称随意
        logger.warn("收到消息 [{}] ", msg);
    }
}
