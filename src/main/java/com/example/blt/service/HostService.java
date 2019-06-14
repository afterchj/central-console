package com.example.blt.service;

import com.example.blt.dao.HostDao;
import com.example.blt.entity.HostInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hongjian.chen on 2019/6/14.
 */
@Service
public class HostService {

    @Autowired
    private HostDao hostDao;

    public void save(HostInfo info) {
        hostDao.save(info);
    }

    public void updateByIp( String ip,boolean status) {
        hostDao.updateByIp(ip,status);
    }
    public void deleteByIp(String ip) {
        hostDao.deleteByIp(ip);
    }
    public HostInfo getByIp(String ip) {
        return hostDao.getByIp(ip);
    }

    public List<HostInfo> getAll() {
        return hostDao.findAll();
    }
}
