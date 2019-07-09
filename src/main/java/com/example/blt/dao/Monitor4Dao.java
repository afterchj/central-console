package com.example.blt.dao;

import com.example.blt.entity.LightDemo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by chenhao.lu on 2019/7/8.
 */
@Mapper
public interface Monitor4Dao {


    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and  (i.y is not null)and d.other='intelligence' Group by mname")
    List<Map<String,Object>> getIntelligenceCenterLNum();//每一层灯个数


    @Select("select count(*) as PlaceLNum,d.mname,d.place from f_light_demo d,t_light_info i where d.lmac=i.lmac and (i.y is not null) and d.other='intelligence' Group by mname,place")
    List<LightDemo> getIntelligencePlaceLNum();//每个区域的灯个数


    @Select("SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0'  when i.y is null and i.status is null then null  END AS status,d.Group  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info Group by lmac) i ON d.lmac = i.lmac where d.other='intelligence' ORDER BY d.mname,d.group")
    List<LightDemo> getIntelligenceLightInfo();

    @Select("select mname,count(*) as PlaceLNum,place from f_light_demo where other =#{other} Group by mname,place ORDER BY mname,place")
    List<LightDemo> getPlaceLNum(@Param("other") String other);//每个区域的灯个数


    @Select("select  lmac ,mname,lname,#{status} as status,d.group from f_light_demo d where other ='intelligence' ORDER BY" +
            " mname,d.group")
    List<LightDemo>  getMonitorFromRemoteByStatus(@Param("status") String status,@Param("other") String other);//全开全关

    @Select("select  lmac ,mname,lname,#{status} as status,d.group from f_light_demo d where other =#{other} and" +
            " d.group=#{group} ORDER BY mname,lname")
    List<LightDemo> getMonitorFromPhoneByGroup(@Param("group") int groupId, @Param("status") String status,@Param("other") String other);//单组全开全关

}
