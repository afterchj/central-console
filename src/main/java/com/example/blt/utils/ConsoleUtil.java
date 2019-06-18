package com.example.blt.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by hongjian.chen on 2019/5/31.
 */
public class ConsoleUtil {

    private static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    public static void saveHosts(Set<Map> list) {
        //Write Obj to File
        File file = new File("/temp/hosts/", "hosts");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(list);
        } catch (IOException e) {
            logger.error("IOException:saveHosts " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(oos);
        }
    }

    public static Set<Map> persistHosts() {
        File file = new File("/temp/hosts/", "hosts");
        //Read Obj from File
        ObjectInputStream ois = null;
        Set<Map> list = new HashSet<>();
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            list = (Set<Map>) ois.readObject();
            Map map = new HashMap();
            map.put("list", list);
        } catch (IOException e) {
            logger.error("IOException:persistHosts " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException:persistHosts " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(ois);
        }
        return list;
    }

    public static void main(String[] args) {
        Set<Map> list = new HashSet<>();
        for (int i = 0; i < 11; i++) {
            Map map = new HashMap();
            if (i % 2 == 0) {
                map.put("vaddr", "1");
                map.put("lmac", "11");
            } else {
                map.put("vaddr", "t" + i);
                map.put("lmac", "l" + i);
            }
            list.add(map);
        }
//        saveHosts(list);
        Set<Map> set = persistHosts();
        logger.info("lists=" + JSON.toJSONString(set));
    }
}
