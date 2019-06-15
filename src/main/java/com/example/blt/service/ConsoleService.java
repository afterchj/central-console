//package com.example.blt.service;
//
//import com.example.blt.dao.ConsoleDao;
//import com.example.blt.entity.ConsoleInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created by hongjian.chen on 2019/6/14.
// */
//@Service
//public class ConsoleService {
//
//    @Autowired
//    private ConsoleDao consoleDao;
//
//    public void save(ConsoleInfo info) {
//        consoleDao.save(info);
//    }
//
//    public List<ConsoleInfo> getAll() {
//        return consoleDao.findAll();
//    }
//
//    public ConsoleInfo getByIp(String ip) {
//        return consoleDao.getByIp(ip);
//    }
//}
