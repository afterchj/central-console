package com.example.blt.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @program: central-console
 * @description: @Cacheable
 * @author: Mr.Ma
 * @create: 2019-06-11 15:37
 **/
@Service
public class CacheableService {

    @Cacheable(cacheNames = "msg",key = "#type")
    public String getCache(String type){
        return "1";
    }

    @CachePut(cacheNames = "msg",key ="#type" )
    public void setCache(String type,String msg){
    }
}
