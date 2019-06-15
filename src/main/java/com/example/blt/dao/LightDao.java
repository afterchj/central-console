//package com.example.blt.dao;
//
//import com.example.blt.entity.HostInfo;
//import com.example.blt.entity.LightInfo;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//
///**
// * Created by hongjian.chen on 2019/6/13.
// */
//public interface LightDao extends JpaRepository<LightInfo, Integer> {
//
//
//    List<LightInfo> getByHostInfo(HostInfo info);
//
////    @Transactional()
////    @Modifying
////    @Query("update HostInfo set status=?2 where ip=?1")
////    void updateByIp(String ip, boolean status);
////
////    @Transactional()
////    @Modifying
////    @Query("delete from HostInfo where ip=?1")
////    void deleteByIp(String ip);
//}
