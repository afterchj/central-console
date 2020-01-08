package com.example.blt.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-12-17 14:42
 **/
public class IpAddressUtil {

    /**
     * 各种代理
     *
     * X-Forwarded-For：Squid服务代理
     * Proxy-Client-IP：apache服务代理
     * WL-Proxy-Client-IP：weblogic服务代理
     * X-Real-IP：nginx服务代理
     * HTTP_CLIENT_IP：有些代理服务器
     */
    private static final String[] PROXYS = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "X-Real-IP", "HTTP_CLIENT_IP"};
    /**
     * 获取客户端ip
     */
    public static String getRealIpAddress(HttpServletRequest request) {
        String ipAddress = null;

        try {
            for (String proxy: PROXYS) {
                ipAddress = request.getHeader(proxy);
                if (StringUtils.isNotBlank(ipAddress) && !"unknown".equalsIgnoreCase(ipAddress)) {
                    return ipAddress;
                }
            }
            if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length() = 15
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
