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

    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and i.x is not null and i.y is not null group by mname")
    List<Map<String,Object>> getCenterLNum();//每一楼灯个数

    @Select("select count(*) as PlaceLNum,d.mname,d.place from f_light_demo d,t_light_info i where d.lmac=i.lmac and i.x is not null and i.y is not null group by mname,place")
    List<LightDemo> getPlaceLNum();//每个区域的灯个数

    @Select("SELECT d.lmac,d.mname,d.lname  ,CASE WHEN i.x = '32' AND i.y = '32' THEN '1' WHEN i.x is not NULL AND ( i.x != '32' OR i.y != '32' ) THEN '0' ELSE NULL END AS STATUS,d.group FROM f_light_demo d LEFT JOIN (select  lmac,x ,y from t_light_info group by lmac) i ON d.lmac = i.lmac ORDER BY d.mname,lname")//1:关 0：开 null 空
    List<LightDemo> getLightInfo();
}
