package com.example.blt.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-06-11 16:10
 **/
@Configuration
public class CacheConfig {

    @Bean
    public GuavaCacheManager getGuavaCacheManager() {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        guavaCacheManager.setCacheBuilder(CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(100, TimeUnit.DAYS));
        ArrayList<String> guavaCacheNames = Lists.newArrayList();
        guavaCacheNames.add("msg");
        guavaCacheManager.setCacheNames(guavaCacheNames);
        return guavaCacheManager;
    }

}
