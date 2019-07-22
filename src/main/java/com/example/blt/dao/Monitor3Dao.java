package com.example.blt.dao;

import com.example.blt.entity.LightDemo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-02 13:32
 **/
@Mapper
public interface Monitor3Dao {

    @Select("select count(*) as PlaceLNum,Place from f_light_demo where mname ='exhibition' Group by mname,Place")
    List<LightDemo> getPlaceLNum();//每个区域的灯个数
}
