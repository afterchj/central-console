package com.example.blt.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongjian.chen
 * @date 2019/8/28 14:06
 */

@Service
public class BLTservice {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public String getHostId(String host) {
        return sqlSessionTemplate.selectOne("console.getHostId", host);
    }
    public List<String> getHosts() {
        return  sqlSessionTemplate.selectList("console.getHosts");
    }

}
