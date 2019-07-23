package com.example.blt.dao;

import com.example.blt.entity.CenterException;
import com.example.blt.entity.LightDemo;
import com.example.blt.entity.Place;
import com.example.blt.entity.PlaceWeb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description: 事件驱动Dao
 * @author: Mr.Ma
 * @create: 2019-07-23 11:44
 **/
@Mapper
public interface EventDrivenDao {

    @Select("select mname from  t_light_info2  where other='intelligence' and y is null and status is null group by substring_index(mname,'楼',1)+0")
    List<String> getException();

    @Select("SELECT mname,lname ,CASE when status  ='0' then '1' when status ='1' then '0' END AS status,Place,group_id as groupId,CONCAT((100-y*5),'%') AS y  FROM  t_light_info2 where other='intelligence'")
    List<LightDemo> getIntelligenceLightInfo();

    @Select("select count(*) as centerLNum,mname from t_light_info2  where status is not null and other='intelligence' Group by substring_index(mname,'楼',1)+0")
    List<Map<String,Object>> getIntelligenceCenterLNum();

    @Select("select mname,count(*) as exception from t_light_info2 where other='intelligence' and status is null Group by substring_index(mname,'楼',1)+0")
    List<CenterException> getIndexFloorException();

    @Select("select mname,Place,count(*) as exception from t_light_info2 where other='intelligence' and status is null Group by substring_index(mname,'楼',1)+0,Place")
    List<Place> getPlacesExceptions();

    @Select("select mname,place,group_id as groupId FROM t_light_info2 where other='intelligence' and status='1' group by substring_index(mname,'楼',1)+0,place,groupId")
    List<LightDemo> getGroupOnStatus();

    @Select("select mname,place,group_id as groupId FROM t_light_info2 where other='intelligence' and status='0' group by substring_index(mname,'楼',1)+0,place,groupId")
    List<LightDemo> getGroupOffStatus();

    @Select("SELECT mname,lname ,CASE when status  ='0' then '1' when status ='1' then '0' END AS status,Place,group_id as groupId,CONCAT((100-y*5),'%') AS y  FROM t_light_info2 where other='intelligence' and mname=#{floor}")
    List<LightDemo> getFloorLights(@Param("floor") String floor);

    @Select("select Place,count(*) as exception from t_light_info2 where other='intelligence' and status is null and mname=#{floor} Group by Place")
    List<PlaceWeb> getPlacesExceptionsByFloor(@Param("floor")String floor);

    @Select("select count(*) as centerLNum from t_light_info2 where status is not null and other='intelligence' and mname=#{floor}")
    Integer getIntelligenceCenterLNumByFloor(@Param("floor")String floor);
}
