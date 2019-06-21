package com.example.blt.utils;

import com.example.blt.entity.ConsoleKeys;
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

    public static void saveVaddr(String key, Set list, int expire) {
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        operations.set(key, list, expire, TimeUnit.HOURS);
    }

    public static void saveLmac(String key, Set list, int expire) {
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        operations.set(key, list, expire, TimeUnit.SECONDS);
    }

    public static void saveInfo(String key,List list) {
        ValueOperations<String, List> operations = redisTemplate.opsForValue();
        operations.set(key, list, 30, TimeUnit.SECONDS);
    }
    public static Set getInfo(String key) {
        return  (Set) redisTemplate.opsForValue().get(key);
    }

    public static List getValueTest(String key) {
        return (List) redisTemplate.opsForValue().get(key);
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
        } else {
            num = sqlSessionTemplate.selectOne("console.getTotal");
        }
        return num;
    }

    public static void main(String[] args) {
        List<Map> vaddr=sqlSessionTemplate.selectList("console.getVaddr");
        System.out.println(getLightSize(new String[]{}));
        String key1 = ConsoleKeys.VADDR.getValue();
        String key2 = ConsoleKeys.lMAC.getValue();
        saveInfo("test_vaddr",vaddr);
        List set1 = getValueTest("test_vaddr");
        saveInfo("test_lmac",set1);
        List set2 = getValueTest("test_lmac");
//        Set set2 = getInfo(key2);
        logger.info("lists=" + set1);
        logger.info("lists=" + set2);
    }
}
