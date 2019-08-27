package com.example.blt.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hongjian.chen
 * @date 2019/8/27 15:46
 */

@Service
public class CloseListener implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.warn("nettyService stopping...");
        sqlSessionTemplate.selectOne("console.refreshHost");
    }
}
