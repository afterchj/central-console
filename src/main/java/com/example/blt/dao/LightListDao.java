package com.example.blt.dao;

import com.example.blt.entity.LightDemo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-06-14 14:01
 **/
@Mapper
public interface LightListDao {

    @Select("select * from f_light_demo")
    List<LightDemo> findLightDemo();

    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and i.x is not null and i.y is not null Group by mname")
    List<Map<String,Object>> getCenterLNum();//每一楼灯个数

    @Select("select count(*) as PlaceLNum,d.mname,d.place from f_light_demo d,t_light_info i where d.lmac=i.lmac and i.x is not null and i.y is not null Group by mname,place")
    List<LightDemo> getPlaceLNum();//每个区域的灯个数

    @Select("SELECT d.lmac,d.mname,d.lname  ,CASE WHEN i.x = '32' AND i.y = '32' THEN '1' WHEN i.x is not NULL AND ( i.x != '32' OR i.y != '32' ) THEN '0' ELSE NULL END AS STATUS,d.Group FROM f_light_demo d LEFT JOIN (select  lmac,x ,y from t_light_info Group by lmac) i ON d.lmac = i.lmac ORDER BY d.mname,lname")//1:关 0：开 null 空
    List<LightDemo> getLightInfo();

    @Select("SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0' when i.y is null and i.status ='0' then '1' when i.y is null and i.status ='1' then '0'  when i.y is null and i.status is null then null  END AS status,d.Group  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info Group by lmac) i ON d.lmac = i.lmac where d.mname='Office' ORDER BY d.mname,lname")
    List<LightDemo> getOfficeLightInfo();

    @Select("select count(*) as PlaceLNum,d.mname,d.place from f_light_demo d,t_light_info i where d.lmac=i.lmac and (i.y is not null or i.status is not null) and d.mname='Office' Group by mname,place")
    List<LightDemo> getOfficePlaceLNum();//每个区域的灯个数

    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and  (i.y is not null or i.status is not null)and d.mname='Office' Group by mname")
    List<Map<String,Object>> getOfficeCenterLNum();//每一楼灯个数


//    @Select("SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0' when i.y is null and i.status ='0' then '1' when i.y is null and i.status ='1' then '0'  when i.y is null and i.status is null then null  END AS status,d.Group  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info Group by lmac) i ON d.lmac = i.lmac where d.mname='exhibition' ORDER BY d.mname,lname")
//    List<LightDemo> getExhibitionLightInfo();

    @Select("SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0'  when i.y is null and i.status is null then null  END AS status,d.Group  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info Group by lmac) i ON d.lmac = i.lmac where d.mname='exhibition' ORDER BY d.mname,lname")
    List<LightDemo> getExhibitionLightInfo();

//    @Select("select count(*) as PlaceLNum,d.mname,d.place from f_light_demo d,t_light_info i where d.lmac=i.lmac and (i.y is not null or i.status is not null) and d.mname='exhibition' Group by mname,place")
//    List<LightDemo> getExhibitionPlaceLNum();//每个区域的灯个数

    @Select("select count(*) as PlaceLNum,d.mname,d.place from f_light_demo d,t_light_info i where d.lmac=i.lmac and (i.y is not null) and d.mname='exhibition' Group by mname,place")
    List<LightDemo> getExhibitionPlaceLNum();//每个区域的灯个数

//    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and  (i.y is not null or i.status is not null)and d.mname='exhibition' Group by mname")
//    List<Map<String,Object>> getExhibitionCenterLNum();//每一楼灯个数

    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and  (i.y is not null)and d.mname='exhibition' Group by mname")
    List<Map<String,Object>> getExhibitionCenterLNum();//每一楼灯个数

    @Select("SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32'  THEN '0'  END AS status,d.Group FROM f_light_demo d JOIN (select  lmac,x ,y,status from t_light_info where y is not null Group by lmac) i ON d.lmac = i.lmac where d.mname='exhibition' ORDER BY d.mname,lname")
    List<LightDemo> getExhibitionLightOnOrOff();



}
