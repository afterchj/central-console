package com.example.blt.service;

import com.example.blt.entity.vo.ConsoleVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongjian.chen
 * @date 2019/8/28 14:06
 */

@Service
public class BLTService {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public String getHostId(String host) {
        return sqlSessionTemplate.selectOne("console.getHostId", host);
    }

    public List<String> getHosts() {
        return sqlSessionTemplate.selectList("console.getHosts");
    }

    public List<String> getAll() {
        return sqlSessionTemplate.selectList("console.getAll");
    }

    public List getHostInfo() {
        return sqlSessionTemplate.selectList("console.getHostInfo");
    }

    public List getHost() {
        return sqlSessionTemplate.selectList("console.getHostInfo");
    }

    public void refreshHost() {
        sqlSessionTemplate.selectOne("console.refreshHost");
    }

    public void saveHost(ConsoleVo consoleVo) {
        sqlSessionTemplate.update("console.saveHost", consoleVo);
    }
}
