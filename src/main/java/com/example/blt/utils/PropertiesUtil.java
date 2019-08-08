package com.example.blt.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by hongjian.chen on 2019/5/30.
 */
public class PropertiesUtil {

    public static String getValue(String key) {
        Properties pro = new Properties();
        try {
            InputStream in = PropertiesUtil.class.getResourceAsStream("/application.properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in,"gbk"));
            pro.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro.getProperty(key, "127.0.0.1");
    }
}
