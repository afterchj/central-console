package com.example.blt.dao;

import com.example.blt.entity.HostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Created by hongjian.chen on 2019/6/13.
 */
public interface HostDao extends JpaRepository<HostInfo, Integer> {
    HostInfo getByIp(String ip);

    @Transactional()
    @Modifying
    @Query("update HostInfo set status=?2 where ip=?1")
    void updateByIp(String ip, boolean status);

    @Transactional()
    @Modifying
    @Query("delete from HostInfo where ip=?1")
    void deleteByIp(String ip);
}
