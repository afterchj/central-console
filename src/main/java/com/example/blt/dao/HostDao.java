package com.example.blt.dao;

import com.example.blt.entity.HostInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hongjian.chen on 2019/6/13.
 */
public interface HostDao extends JpaRepository<HostInfo, Integer> {
    HostInfo getByIp(String ip);
}
