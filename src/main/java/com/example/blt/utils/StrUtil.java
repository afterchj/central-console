package com.example.blt.utils;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by after on 2019/6/14.
 */
public class StrUtil {

    private static Logger logger = LoggerFactory.getLogger(StrUtil.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    public static Map buildLightInfo(String str, String ip) {
        Map map = new HashMap();
        String msg = str.replace(" ", "");
        map.put("host", ip);
        map.put("other", msg);
        map.put("status", 1);
        if (msg.indexOf("77040F0") != -1) {
            String prefix = msg.substring(0, 8);
            String lmac = msg.substring(8, 20).toLowerCase();
            String vaddr = msg.substring(20, 28);
            String productId = msg.substring(28, 32);
            System.out.println(prefix + "\t" + lmac + "\t" + vaddr + "\t" + productId);
            map.put("vaddr", vaddr);
            map.put("product_id", productId);
            String[] strArr = buildStr(lmac);
            StringBuffer sortMac = new StringBuffer();
            for (int i = strArr.length - 1; i >= 0; i--) {
                if (i != 0) {
                    sortMac.append(strArr[i] + ":");
                } else {
                    sortMac.append(strArr[i]);
                }
            }
            map.put("lmac", sortMac.toString());
            sqlSessionTemplate.selectOne("console.saveConsole", map);
            logger.info("vaddr=" + vaddr + ",result=" + map.get("result"));
        }
        return map;
    }

    public static void buildLightInfo1(String str, String ip) {
        Map map = new HashMap();
        String msg = str.replace(" ", "");
        String str1 = "77040F0227";
        map.put("status", 1);
        map.put("host", ip);
        if (msg.indexOf(str1) != -1) {
            String vaddr = str.substring(str1.length(), str1.length() + 8);
            if (msg.contains("3232")) {
                map.put("status", 0);
            }
            map.put("vaddr", vaddr);
            sqlSessionTemplate.selectOne("console.saveConsole", map);
            logger.info("vaddr=" + vaddr + ",result=" + map.get("result"));
        }
    }


//    public static void main(String args[]) {
//        String str = "77 04 0F 01 A9 10 64 D7 AC F0 7D 00 00 00 44 4F 03 0A CC CC ";
//        String str1 = "77 04 0F 01 F1 10 64 D7 AC F0 3D 00 00 00 44 4F 01 0A CC CC ";
//        String str2 = "77 04 0F 01 A8 10 64 D7 AC F0 0D 00 00 00 44 4F 02 0A CC CC  ";
//        buildLightInfo(str2);
//    }

    public static String[] buildStr(String str) {
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
        return buffer.toString().split(":");
    }
}
