package com.example.blt.service;

import com.example.blt.dao.TpadOfficeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 11:17
 **/
@Service
public class TpadOfficeService {

    @Resource
    private TpadOfficeDao tpadOfficeDao;

    public String getHostId(String projectName) {
        return tpadOfficeDao.getHostId(projectName);
    }

    public List<Map<String,Object>> getParameterSetting() {
        return tpadOfficeDao.getParameterSetting();
    }
}
