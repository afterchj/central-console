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

    @Select("select count(*) as centerLNum,mname from f_light_demo group by mname")
    List<Map<String,Object>> getCenterLNum();//每一楼灯个数

    @Select("select count(*) as PlaceLNum,mname,place from f_light_demo group by mname,place")
    List<LightDemo> getPlaceLNum();//每个区域的灯个数
}
