package com.example.blt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author hongjian.chen
 * @date 2019/7/9 11:46
 */
public class AddrUtil {

    private static Logger logger = LoggerFactory.getLogger(AddrUtil.class);

    public static String getIp(boolean flag) {
        InetAddress addr;
        String ip = "127.0.0.1";
        if (flag) {
            try {
                addr = InetAddress.getLocalHost();
                ip = addr.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        logger.warn("Local HostAddress:" + ip);
        return ip;
    }

    public static void main(String[] args) {
        System.out.println("ip=" + getIp(false));
    }
}
