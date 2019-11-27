package com.example.blt.utils;

import java.util.Map;
import java.util.TreeMap;
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
            if ("luminances".equals(name) && i==0){
                i += 5;
            }
            hexStr = String.format("%02x",count).toUpperCase();
            map.put(String.valueOf(i),hexStr);
            count = "colors".equals(name)?count + 1:count - 1;
        }
        return map;
    }

    public static Map<String,String> toDecimalism(String name){
        String[] xKeyArr = {"00", "01", "02", "03","04","05","06","07","08","09","0A","0B","0C","0D","0E","0F","10","11","12","13","14"};
        String[] xValueArr = { "0","5","10","15","20","25","30","35","40","45","50","55","60","65","70","75","80","85","90","95","100"};
        String[] yKeyArr = {"00", "01", "02", "03","04","05","06","07","08","09","0A","0B","0C","0D","0E","0F","10","11","12","13"};
        String[] yValueArr = { "5","10","15","20","25","30","35","40","45","50","55","60","65","70","75","80","85","90","95","100"};
        String[] keyArr = "colors".equals(name)?xKeyArr:yKeyArr;
        String[] valueArr = "colors".equals(name)?xValueArr:yValueArr;
        Map<String,String> map = new TreeMap<>();
        int len = keyArr.length;
        for (int i=0;i<len;i++){
            String key = keyArr[i];
            String value = "colors".equals(name)?valueArr[i]:valueArr[len-1-i];
            map.put(key,value);
        }
        return map;
    }
}
