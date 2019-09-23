package com.example.blt.utils;

import com.alibaba.fastjson.JSON;
import com.example.blt.config.WebSocket;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.entity.dd.Topics;
import com.example.blt.service.ProducerService;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by hongjian.chen on 2019/6/14.
 */
public class StringBuildUtils {

    private static Logger logger = LoggerFactory.getLogger(StringBuildUtils.class);
    private static Set<String> ipSet = new CopyOnWriteArraySet<>();
    private static Set<String> lmacSet = new CopyOnWriteArraySet<>();
    private static Set<String> vaddrSet = new CopyOnWriteArraySet<>();
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    public static void buildLightInfo(String ip, String host, String msg) {
        String[] array = msg.split("CCCC");
        if (array.length == 1) {
            String format = array[0];
            if (format.length() >= 42) {
                tempFormat(format, host);
            }
        }
        for (String str : array) {
            Map map = new HashMap();
            map.put("ip", ip);
            map.put("host", host);
            map.put("status", 1);
            if (str.indexOf("77011365") != -1) {
                ConsoleUtil.cleanSet(lmacSet, vaddrSet, ipSet);
                String vaddr = str.substring(18, 26);
                vaddrSet.add(vaddr);
                ConsoleUtil.saveVaddr(ConsoleKeys.VADDR.getValue(), vaddrSet, 10);
//                ConsoleUtil.saveLight(ConsoleKeys.LINFO.getValue(), ConsoleKeys.VADDR.getValue(), ip, vaddrSet);
                String x = str.substring(28, 30);
                String y = str.substring(30, 32);
                if (str.contains("3232")) {
                    map.put("status", 0);
                } else {
                    map.put("status", 1);
                }
                map.put("vaddr", vaddr);
                map.put("x", x);
                map.put("y", y);
                saveLight(map, false);
            } else if (str.indexOf("77011366") != -1) {
                ConsoleUtil.cleanSet(lmacSet, vaddrSet, ipSet);
                ipSet.add(host);
                String lmac = str.substring(16, 28);
                String vaddr = str.substring(28, 36);
                String productId = str.substring(36, 44);
                map.put("vaddr", vaddr);
                map.put("product_id", productId);
                String mac = sortMac(lmac);
                map.put("lmac", mac);
                lmacSet.add(mac);
                ConsoleUtil.saveLmac(ConsoleKeys.lMAC.getValue(), lmacSet, 10);
                ConsoleUtil.saveHost(ConsoleKeys.HOSTS.getValue(), ipSet, 10);
                saveLight(map, false);
            } else if (str.indexOf("77050901") != -1) {
                if (str.length() >= 24) {
                    String meshId = str.substring(8, 24);
                    char[] chars = meshId.toCharArray();
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < chars.length; i++) {
                        if (i % 2 != 0) {
                            buffer.append(chars[i]);
                        }
                    }
                    map.put("meshId", buffer.toString());
                    insertOrUpdateHost(map);
                }
            } else if (str.indexOf("77050208") != -1) {
                String flag = str.substring(8, 10);
                map.put("flag", flag);
                updateHost(map, false);
            } else if (str.indexOf("77050506") != -1) {
                if (str.length() > 16) {
                    str = str.substring(str.length() - 16);
                }
                String temp = StringBuildUtils.sortMac(str.substring(8, 12)).replace(":", "");
                int product = Integer.parseInt(temp, 16);
                String version = str.substring(12);
                map.put("type", product);
                map.put("version", version);
                insertOrUpdateHost(map);
            } else if (str.indexOf("77050705") != -1) {
                for (; ; ) {
                    str += "C";
                    if (str.length() == 20) break;
                }
                String mac = StringBuildUtils.sortMac(str.substring(8));
                map.put("mac", mac);
                insertOrUpdateHost(map);
            }
        }
    }

    public static String buildMac(String str) {
        char[] chars = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (i < chars.length - 1) {
                if (i % 2 != 0) {
                    buffer.append(chars[i] + ":");
                } else {
                    buffer.append(chars[i]);
                }
            } else {
                buffer.append(chars[i]);
            }
        }
        return buffer.toString();
    }

    public static String sortMac(String lmac) {
        String[] strArr = buildMac(lmac).split(":");
        StringBuffer sortMac = new StringBuffer();
        for (int i = strArr.length - 1; i >= 0; i--) {
            if (i != 0) {
                sortMac.append(strArr[i] + ":");
            } else {
                sortMac.append(strArr[i]);
            }
        }
        return sortMac.toString().toLowerCase();
    }

    public static void tempFormat(String format, String ip) {
        String mid = format.substring(16, 18);
        String str = format.substring(26);
        int len = str.length();
        String cid = str.substring(len - 2);
        String prefix = str.substring(0, 2).toUpperCase();
        String x = str.substring(4, 6);
        String y = str.substring(6, 8);
        int status = 0;
        Map map = new ConcurrentHashMap<>();
        map.put("host", ip);
        if (str.contains("3232")) {
            map.put("status", 0);
        } else {
            map.put("status", 1);
        }
//        logger.warn("format [{}] prefix [{}]", str,prefix);
        switch (prefix) {
            case "42":
                map.put("ctype", prefix);
                map.put("cid", cid);
                break;
            case "52"://52表示遥控器控制命令，01,02字段固定，01表示开，02表示关
                String cmd = str.substring(len - 4, len - 2);
                if ("01".equals(cmd)) {
                    status = 0;
//                    ClientMain.sendCron(AddrUtil.getIp(false), Groups.GROUPSA.getOn());
                } else if ("02".equals(cmd)) {
                    status = 1;
//                    ClientMain.sendCron(AddrUtil.getIp(false), Groups.GROUPSA.getOff());
                }
                map.put("ctype", prefix);
                map.put("cid", cmd);
                map.put("status", status);
                break;
            case "C0"://pad或手机，C0代表全控，37 37字段是x、y值
                map.put("ctype", prefix);
                map.put("cid", mid);
                map.put("x", x);
                map.put("y", y);
                break;
            case "C3"://pad或手机，C0代表全控，37 37字段是x、y值
                map.put("ctype", prefix);
                map.put("cid", mid);
                map.put("x", str.substring(4, 8));
                map.put("y", str.substring(8, 10));
                break;
            case "CA":
                //门磁,77 04 0E 02 20 9D 01 00 00 CA 00  关门,77 04 0E 02 20 9D 01 00 00 CA 01   开门
                map.put("ctype", "CA");
                map.put("cid", cid);
                break;
            case "CB":
//                人感 ,77 04 0E 02 20 9D 01 00 00 CB 00  无人,77 04 0E 02 20 9D 01 00 00 CB 01  有人
                map.put("ctype", "CB");
                map.put("cid", cid);
                break;
            case "CC":
//                温湿度 77 04 0E 02 20 9D 01 00 00 CC, 温度 00 00 湿度  00 00
                map.put("ctype", prefix);
                map.put("cid", mid);
                map.put("x", str.substring(30, 38));
                map.put("y", str.substring(38, 42));
                break;
            default:
//                场景77 04 0E 02 20 9D 01 00 00 42 00 00 00 00 00 00 02 83
                //42代表场景控制，02字段是场景ID
//               77 04 10 02 20 05 00 00 00 C1 32 32 00 00 00 00 00 00 01 CC CC
                //C1代表组控，32 32字段是x、y值, 02字段是组ID
//               C4，RGB组控77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F
                if ("C1".equals(prefix) || "C4".equals(prefix)) {
                    map.put("ctype", prefix);
                    map.put("x", x);
                    map.put("y", y);
                    map.put("cid", cid);
                }
                break;
        }
        if (!map.containsKey("ctype")) return;
        saveConsole(Topics.CONSOLE_TOPIC.getTopic(), map, false);
    }

    public static void parseLocalCmd(String str, String ip) {
        Map map = new ConcurrentHashMap();
        String prefix = str.substring(0, 8);
        if (str.contains("3232")) {
            map.put("status", 0);
        } else {
            map.put("status", 1);
        }
        map.put("host", ip);
        String cmd = str.substring(prefix.length());
        Integer cid = Integer.parseInt(cmd.substring(0, 2), 16);
        switch (prefix) {
            case "77010416":
                map.put("ctype", "CW");
                map.put("x", cmd.substring(2, 4));
                map.put("y", cmd.substring(4, 6));
                map.put("cid", cid);
                break;
            case "77010315":
                map.put("ctype", "C0");
                map.put("x", cmd.substring(0, 2));
                map.put("y", cmd.substring(2, 4));
                break;
            case "77010219":
                map.put("ctype", "42");
                map.put("cid", cid);
                break;
            default:
                break;
        }
        if (!map.containsKey("ctype")) return;
        saveConsole(Topics.LOCAL_TOPIC.getTopic(), map, false);
    }

    public static void saveLight(Map map, boolean flag) {
        if (flag) {
            try {
                ProducerService.pushMsg(Topics.LIGHT_TOPIC.getTopic(), JSON.toJSONString(map));
            } catch (Exception e) {
            }
        }
        sqlSessionTemplate.selectOne("console.saveLight", map);
    }

    public static void saveConsole(String topic, Map map, boolean flag) {
        if (flag) {
            try {
                String info = JSON.toJSONString(map);
                ProducerService.pushMsg(topic, info);
            } catch (Exception e) {
            }
        }
        sqlSessionTemplate.selectOne("console.saveConsole", map);
        WebSocket.sendMessage(map);
    }

    public static void insertOrUpdateHost(Map map) {
        logger.warn("map {}", map);
        saveHost(map, false);
    }

    public static void saveHost(Map map, boolean flag) {
        if (flag) {
            try {
                ProducerService.pushMsg(Topics.HOST_TOPIC.getTopic(), JSON.toJSONString(map));
            } catch (Exception e) {
            }
        }
        sqlSessionTemplate.insert("console.saveUpdateHosts", map);
    }

    public static void updateHost(Map map, boolean flag) {
        if (flag) {
            try {
                ProducerService.pushMsg(Topics.HOST_UPDATE_TOPIC.getTopic(), JSON.toJSONString(map));
            } catch (Exception e) {
            }
        }
        sqlSessionTemplate.update("console.updateHostsFlag", map);
    }
}
