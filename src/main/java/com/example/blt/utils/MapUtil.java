package com.example.blt.utils;

import org.thymeleaf.util.ArrayUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by hongjian.chen on 2019/6/18.
 */
public class MapUtil {

    /**
     *  Map中根据key批量删除键值对
     * @param map
     * @param excludeKeys
     * @param <K>
     * @param <V>
     * @return
     */

    public static <K, V> Map removeEntries(Map<K, V> map, K[] excludeKeys) {
        Iterator<K> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            K k = iterator.next();
            // 如果k刚好在要排除的key的范围中
            if (!ArrayUtils.contains(excludeKeys, k)) {
                iterator.remove();
                map.remove(k);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        Set<Map> maps=ConsoleUtil.persistHosts();
        for (Map map:maps){
            removeEntries(map,new String[]{"vaddr"});
            System.out.println(map);
        }
    }
}
