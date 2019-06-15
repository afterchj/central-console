package com.example.blt.utils;

import com.alibaba.fastjson.JSON;
import com.example.blt.entity.ConsoleInfo;
import com.example.blt.entity.HostInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/6/14.
 */
public class ConsoleUtil {

    private static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();


//    private static ConsoleService consoleService = (ConsoleService) SpringJpaUtil.getBean(ConsoleService.class);
//    private static HostService hostService = (HostService) SpringJpaUtil.getBean(HostService.class);
//
//    public static void saveConsole(ConsoleInfo info) {
//        consoleService.save(info);
//    }
//
//    public static void saveHostInfo(HostInfo info) {
//        hostService.save(info);
//    }
//
//    public static void updateHost(String ip,boolean status) {
//        hostService.updateByIp(ip,status);
//    }
//    public static void deleteHost(String ip) {
//        hostService.deleteByIp(ip);
//    }

    public static void main(String[] args) {
        List<Map> list=sqlSessionTemplate.selectList("console.getLights");
        logger.info(JSON.toJSONString(list));
    }
}
