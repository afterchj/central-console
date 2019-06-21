package com.example.blt.utils;

import org.apache.commons.io.IOUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by hongjian.chen on 2019/5/31.
 */
public class ConsoleUtil {

    private static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static RedisTemplate redisTemplate = SpringUtils.getRedisTemplate();

    public static void saveHosts(Set list) {
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

    public static Set persistHosts() {
        File file = new File("/temp/hosts/", "hosts");
        //Read Obj from File
        ObjectInputStream ois = null;
        Set list = new HashSet<>();
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            list = (Set) ois.readObject();
        } catch (IOException e) {
            logger.error("IOException:persistHosts " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException:persistHosts " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(ois);
        }
        return list;
    }

    public static void saveInfo(String key, Set list) {
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        operations.set(key, list, 30, TimeUnit.SECONDS);
    }

    public static Set getInfo(String key) {
        return (Set) redisTemplate.opsForValue().get(key);
    }

    public static int getLightSize(String... key) {
        Integer num = 0;
        if (key.length > 0) {
            List<Map<String, Object>> list = sqlSessionTemplate.selectList("console.getLights");
            for (String name : key) {
                for (Map<String, Object> map : list) {
                    Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> entry = it.next();
                        if (entry.getValue().equals(name)) {
                            num += Integer.valueOf(String.valueOf(map.get("lmacn")));
                        }
                    }
                }
            }
        }else {
            num=sqlSessionTemplate.selectOne("console.getTotal");
        }
        return num;
    }

    public static void main(String[] args) {
        System.out.println(getLightSize(new String[]{}));
//        String key = ConsoleKeys.VADDR.getValue();
//        String key2 = ConsoleKeys.lMAC.getValue();
//        saveInfo(key, persistHosts());
//        saveInfo(key2, persistHosts());
//        Set set = getInfo(key);
//        logger.info("lists=" + set);
    }
}
