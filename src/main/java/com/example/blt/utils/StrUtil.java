package com.example.blt.utils;

import com.alibaba.fastjson.JSON;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.entity.dd.Topics;
import com.example.blt.exception.NoTopicException;
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
public class StrUtil {

    private static Logger logger = LoggerFactory.getLogger(StrUtil.class);
    private static Set<String> lmacSet = new CopyOnWriteArraySet<>();
    private static Set<String> vaddrSet = new CopyOnWriteArraySet<>();
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    public static void buildLightInfo(String ip, String... array) {
        ConsoleUtil.cleanSet(lmacSet, vaddrSet);
        for (String str : array) {
            Map map = new HashMap();
//        String str = msg.replace(" ", "");
            String str1 = "77040F0227";
            map.put("host", ip);
            map.put("status", 1);
//            map.put("other", str);
            if (str.indexOf(str1) != -1) {
                map.put("host", ip);
                int index = str1.length();
                String vaddr = str.substring(index, index + 8);
                vaddrSet.add(vaddr);
                ConsoleUtil.saveVaddr(ConsoleKeys.VADDR.getValue(), vaddrSet, 20);
                String x = str.substring(index + 10, index + 12);
                String y = str.substring(index + 12, index + 14);
                if (str.contains("3232")) {
                    map.put("status", 0);
                } else {
                    map.put("status", 1);
                }
                map.put("vaddr", vaddr);
                map.put("x", x);
                map.put("y", y);
                try {
                    ProducerService.pushMsg(Topics.LIGHT_TOPIC.getTopic(), JSON.toJSONString(map));
                } catch (NoTopicException e) {
                    sqlSessionTemplate.selectOne("console.saveLight", map);
                }
            } else if (str.indexOf("77040F01") != -1) {
//            String prefix = str.substring(0, 8);
                String lmac = str.substring(8, 20).toLowerCase();
                String vaddr = str.substring(20, 28);
                String productId = str.substring(28, 32);
                map.put("vaddr", vaddr);
                map.put("product_id", productId);
                String mac = sortMac(lmac);
                map.put("lmac", mac);
                lmacSet.add(mac);
                ConsoleUtil.saveLmac(ConsoleKeys.lMAC.getValue(), lmacSet, 80);
                try {
                    ProducerService.pushMsg(Topics.LIGHT_TOPIC.getTopic(), JSON.toJSONString(map));
                } catch (NoTopicException e) {
                    sqlSessionTemplate.selectOne("console.saveLight", map);
                }
            } else {
                int len = str.length();
                if (len >= 22) {
                    tempFormat(str, ip);
                }
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
        return sortMac.toString();
    }

    public static void tempFormat(String format, String ip) {
        String str = format.substring(18);
        int len = str.length();
        String prefix = str.substring(0, 2).toUpperCase();
        String tmp = str.substring(2, 4);
        String cid = str.substring(len - 4, len - 2);
        Map map = new ConcurrentHashMap<>();
        map.put("host", ip);
        map.put("other", format);
        switch (prefix) {
            case "52"://52表示遥控器控制命令，01,02字段固定，01表示开，02表示关
                //7704100221F505000052456365D7ACF0000200CCCC
//                String cmd = str.substring(len - 6, len - 4);
//                if ("01".equals(cmd)) {
//                    ClientMain.sendCron(AddrUtil.getIp(false), Groups.GROUPSA.getOn());
//                } else if ("02".equals(cmd)) {
//                    ClientMain.sendCron(AddrUtil.getIp(false), Groups.GROUPSA.getOff());
//                }
                map.put("ctype", prefix);
                map.put("cid", cid);
                break;
            case "C0"://pad或手机，C0代表全控，37 37字段是x、y值
                map.put("ctype", prefix);
                map.put("x", str.substring(4, 6));
                map.put("y", str.substring(6, 8));
                break;
            case "42": //42代表场景控制，02字段是场景ID
                map.put("ctype", prefix);
                map.put("cid", cid);
                break;
            case "CA":
                //门磁,77 04 0E 02 20 9D 01 00 00 CA 00  关门,77 04 0E 02 20 9D 01 00 00 CA 01   开门
                map.put("ctype", prefix);
                map.put("x", tmp);
                break;
            case "CB":
//                人感 ,77 04 0E 02 20 9D 01 00 00 CB 00  无人,77 04 0E 02 20 9D 01 00 00 CB 01  有人
                map.put("ctype", "CB");
                map.put("x", tmp);
                break;
            case "CC":
//                温湿度 77 04 0E 02 20 9D 01 00 00 CC, 温度 00 00 湿度  00 00
                map.put("ctype", prefix);
                map.put("cid", tmp);
                map.put("x", str.substring(4, 8));
                map.put("y", str.substring(8, 12));
                break;
            default:
                //C1代表组控，32 32字段是x、y值, 02字段是组ID
//               C4，RGB组控77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F
//                灯状态信息77 04 0F 02 27 35 00 00 00 71 00 13 00 00 00 00 00 00 0E
                if ("C1".equals(prefix) || "C4".equals(prefix) || "71".equals(prefix)) {
                    map.put("ctype", prefix);
                    map.put("x", str.substring(2, 4));
                    map.put("y", str.substring(4, 6));
                    map.put("cid", cid);
                }
                break;
        }
        try {
            ProducerService.pushMsg(Topics.CONSOLE_TOPIC.getTopic(), JSON.toJSONString(map));
        } catch (NoTopicException e) {
            sqlSessionTemplate.selectOne("console.saveConsole", map);
            logger.warn("result=" + map.get("result"));
        }
//        logger.warn("result=" + JSON.toJSONString(map));
    }

    public static void formatStr(String str, String ip) {
        Map map = new ConcurrentHashMap<>();
        String prefix = str.substring(0, 2);
        map.put("host", ip);
        map.put("lmac", str.substring(2, 14));
        map.put("mesh_id", str.substring(14, 22));
//        map.put("other", str);
        switch (prefix) {
            case "02":
                map.put("cid", str.substring(34, 36));
                map.put("x", str.substring(36, 38));
                map.put("y", str.substring(38, 40));
                break;
            default:
                if ("03".equals(prefix)) {
                    map.put("ctype", str.substring(34, 36));
                    map.put("cid", str.substring(36, 38));
                    map.put("x", str.substring(38, 40));
                    map.put("y", str.substring(40));
                }
                break;
        }
        try {
            ProducerService.pushMsg(Topics.CONSOLE_TOPIC.getTopic(), JSON.toJSONString(map));
        } catch (NoTopicException e) {
            sqlSessionTemplate.selectOne("console.saveConsole", map);
            logger.warn("result=" + map.get("result"));
        }
    }

//    public static void main(String[] args) {
//        tempFormat("77041002216501000052456365D7ACF0000200","");
//    }
}
