package com.example.blt.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 16:14
 **/
public class DimmingUtil {

    public static Map<Integer, String> toAddHexList(String name){
        Map<Integer, String> map = new ConcurrentHashMap<>();
        int count = "colors".equals(name)?0:19;
        String hexStr;
        for (int i = 0; i <= 100; i = i + 5) {
            hexStr = String.format("%02x",count).toUpperCase();
            if ("luminances".equals(name) && i == 100){
                hexStr = "00";
            }
            map.put(i,hexStr);
            count = "colors".equals(name)?count + 1:count - 1;
        }
        return map;
    }

    public static Map<String, String> toAddHexList2(String name){
        Map<String, String> map = new ConcurrentHashMap<>();
        int count = "colors".equals(name)?0:19;
        String hexStr;
        for (int i = 0; i <= 100; i = i + 5) {
            hexStr = String.format("%02x",count).toUpperCase();
            if ("luminances".equals(name) && i == 100){
                hexStr = "00";
            }
            map.put(String.valueOf(i),hexStr);
            count = "colors".equals(name)?count + 1:count - 1;
        }
        return map;
    }
}
