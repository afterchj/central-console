package com.example.blt.dao;

import com.example.blt.entity.ConsoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hongjian.chen on 2019/6/13.
 */
public interface ConsoleDao extends JpaRepository<ConsoleInfo, Integer> {
    ConsoleInfo getByIp(String ip);
}
