package com.example.blt.utils;

import com.example.blt.entity.dd.ConsoleKeys;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by hongjian.chen on 2019/5/31.
 */
public class ConsoleUtil {

    private static Logger logger = LoggerFactory.getLogger(ConsoleUtil.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static RedisTemplate redisTemplate = SpringUtils.getRedisTemplate();

    public static void saveVaddr(String key, Set list, int expire) {
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        operations.set(key, list, expire, TimeUnit.MINUTES);
    }

    public static void saveLmac(String key, Set list, int expire) {
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        operations.set(key, list, expire, TimeUnit.MINUTES);
    }

    public static void saveHost(String key, Set list, int expire) {
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        operations.set(key, list, expire, TimeUnit.MINUTES);
    }

    public static void saveInfo(String key, Integer size) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.set(key, size, 30, TimeUnit.SECONDS);
    }

    public static Set getInfo(String key) {
        return (Set) redisTemplate.opsForValue().get(key);
    }

    public static void cleanKey(String... keys) {
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    public static Map getLight(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public static void saveLight(String key, String hkey, String ip, Set set) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, "ip", ip);
        hashOperations.put(key, hkey, set);
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }

    public static void cleanSet(Set lmacSet, Set vaddrSet, Set ipSet) {
        Map map = ConsoleUtil.getLight(ConsoleKeys.LINFO.getValue());
        Set lmac = (Set) map.get(ConsoleKeys.lMAC.getValue());
        Set vaddr = (Set) map.get(ConsoleKeys.VADDR.getValue());
//        Set lmac = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
//        Set vaddr = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
        Set ips = ConsoleUtil.getInfo(ConsoleKeys.HOSTS.getValue());
        if (lmac == null) {
            lmacSet.clear();
        }
        if (vaddr == null) {
            vaddrSet.clear();
        }
        if (ips == null) {
            ipSet.clear();
        }
    }

    public static Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static int getLightSize(String... key) {
        Integer num = 0;
        if (key.length > 0) {
            List<Map<String, Object>> list = sqlSessionTemplate.selectList("console.getLights");
            for (String name : key) {
                for (Map<String, Object> map : list) {
                    System.out.println(map.get("mname") + "\t" + map.get(name));
                    num += Integer.valueOf(String.valueOf(map.get(name)));
                }
            }
        } else {
            num = sqlSessionTemplate.selectOne("console.getTotal");
        }
        return num;
    }
}
