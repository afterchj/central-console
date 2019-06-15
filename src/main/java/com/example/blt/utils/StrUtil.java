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

    public static Map buildLightInfo(String msg, String ip) {
        Map map = new HashMap();
        String str = msg.replace(" ", "");
        String str1 = "77040F0227";
        map.put("host", ip);
        map.put("status", 1);
        if (str.indexOf(str1) != -1) {
            map.put("host", ip);
            int index = str1.length();
            String vaddr = str.substring(index, index + 8);
            String x = str.substring(index + 10, index + 12);
            String y = str.substring(index + 12, index + 14);
            if (msg.contains("3232")) {
                map.put("status", 0);
            } else {
                map.put("status", 1);
            }
            map.put("vaddr", vaddr);
            map.put("x", x);
            map.put("y", y);
            map.put("other", str);
            sqlSessionTemplate.selectOne("console.saveConsole", map);
            logger.info("result=" + map.get("result"));
        } else if (str.indexOf("77040F01") != -1) {
            String prefix = str.substring(0, 8);
            String lmac = str.substring(8, 20).toLowerCase();
            String vaddr = str.substring(20, 28);
            String productId = str.substring(28, 32);
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
            logger.info("result=" + map.get("result"));
        }
        return map;
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
