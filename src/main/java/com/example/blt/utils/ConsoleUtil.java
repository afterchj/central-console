package com.example.blt.utils;

import org.apache.commons.io.IOUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
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

    public static void saveInfo(Set list) {
        ValueOperations<String,Set> operations = redisTemplate.opsForValue();
        operations.set("t_vaddr", list, 1, TimeUnit.DAYS);
    }

    public static Set getInfo() {
        return (Set) redisTemplate.opsForValue().get("t_vaddr");
    }

    public static void main(String[] args) {
        saveInfo(persistHosts());
        Set set = getInfo();
//        saveInfo(set);
        logger.info("lists=" + set);
    }
}
