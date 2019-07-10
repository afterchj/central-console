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
 * @create: 2019-07-02 13:32
 **/
@Mapper
public interface Monitor2Dao {


    @Select("select  lmac ,mname,lname,#{status} as status,d.group from f_light_demo d where mname =#{mname} ORDER BY" +
            " mname,lname")
    List<LightDemo>  getMonitorFromRemoteByStatus(@Param("status") String status,@Param("mname") String mname);//全开全关

    @Select("select count(*) as PlaceLNum,place from f_light_demo where mname =#{mname} Group by mname,place")
    List<LightDemo> getPlaceLNum(@Param("mname") String mname);//每个区域的灯个数

    @Select("select id from t_command_info where ctype is not null and SUBSTRING_INDEX(SUBSTRING_INDEX(host,'.',-2),'.',1)=#{host} order by id desc limit 1")
    int getCommandId(@Param("host") String host);

}
