package com.example.blt.dao;

import com.example.blt.entity.CenterException;
import com.example.blt.entity.LightDemo;
import com.example.blt.entity.Mnames;
import com.example.blt.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-18 15:23
 **/
@Mapper
public interface NewMonitorDao {

    @Select("select mname,count(*) as exception from f_light_demo d left join t_light_info_copy i on d.lmac=i.lmac where  d.other='intelligence' and i.y is null and i.status is null Group by substring_index(mname,'楼',1)+0")
    List<CenterException> getIndexFloorException();

    @Select("select mname from f_light_demo where other='intelligence' Group by substring_index(mname,'楼',1)+0")
    List<Mnames> getMnames();

    @Select("select mname from f_light_demo where other='intelligence' Group by substring_index(mname,'楼',1)+0")
    List<Map<String,Object>> getMnamesByLeft();

    @Select("select mname,Place,count(*) as exception from f_light_demo d left join t_light_info_copy i on d.lmac=i.lmac where d.other='intelligence' and i.y is null and i.status is null Group by substring_index(mname,'楼',1)+0,Place")
    List<Place> getPlacesExceptions();

    @Select("select mname,Place from f_light_demo  where other='intelligence' Group by substring_index(mname,'楼',1)+0,Place")
    List<Place> getPlaces();

    @Select("SELECT d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0'  when i.y is null and i.status  ='0' then '1' when i.y is null and i.status ='1' then '0'  when i.y is null and i.status is null then null  END AS status,d.Place,d.groupId,CONCAT((100-i.y*5),'%') AS y  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info_copy) i ON d.lmac = i.lmac where d.other='intelligence' and d.mname=#{floor}")
    List<LightDemo> getFloorLights(@Param("floor") String floor);

    @Select("select mname,Place from f_light_demo  where other='intelligence' and mname=#{floor} Group by substring_index(mname,'楼',1)+0,Place")
    List<Place> getPlacesByFloor(@Param("floor")String floor);

    @Select("select mname,Place,count(*) as exception from f_light_demo d left join t_light_info_copy i on d.lmac=i.lmac where d.other='intelligence' and i.y is null and i.status is null and d.mname=#{floor} Group by substring_index(mname,'楼',1)+0,Place")
    List<Place> getPlacesExceptionsByFloor(@Param("floor")String floor);

    @Select("select count(*) as centerLNum,mname from f_light_demo d left join t_light_info_copy i on d.lmac=i.lmac where i.status is not null and  d.other='intelligence' Group by substring_index(mname,'楼',1)+0")
    List<Map<String,Object>> getIntelligenceCenterLNum();
}
