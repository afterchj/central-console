package com.example.blt.dao;

import com.example.blt.entity.CommandLight;
import com.example.blt.entity.LightDemo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-02 13:26
 **/
@Mapper
public interface MonitorDao {

    @Select("select  lmac ,mname,lname,#{status} as status,d.group from f_light_demo d where mname like '%楼' ORDER BY" +
            " mname,lname")
    List<LightDemo> getMonitorFromRemoteByStatus(@Param("status") String status);

    @Select("select  lmac ,mname,lname,#{status} as status,d.group from f_light_demo d where mname like '%楼' and" +
            " d.group=#{group} ORDER BY mname,lname")
    List<LightDemo> getMonitorFromPhoneByGroup(@Param("group") int groupId, @Param("status") String status);

}
