package com.example.blt.utils;

import com.example.blt.entity.ConsoleInfo;
import com.example.blt.entity.HostInfo;
import com.example.blt.service.ConsoleService;
import com.example.blt.service.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hongjian.chen on 2019/6/14.
 */
public class ConsoleUtil {

    private static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class);
    private static ConsoleService consoleService = (ConsoleService) SpringUtil.getBean(ConsoleService.class);
    private static HostService hostService = (HostService) SpringUtil.getBean(HostService.class);

    public static void saveConsole(ConsoleInfo info) {
        consoleService.save(info);
    }

    public static void saveHostInfo(HostInfo info) {
        hostService.save(info);
    }

    public static void updateHost(String ip,boolean status) {
        hostService.updateByIp(ip,status);
    }
    public static void deleteHost(String ip) {
        hostService.deleteByIp(ip);
    }
}
