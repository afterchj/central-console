package com.example.blt.dao;

import com.example.blt.entity.CenterException;
import com.example.blt.entity.Mnames;
import com.example.blt.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("select mname,Place,count(*) as exception from f_light_demo d left join t_light_info_copy i on d.lmac=i.lmac where d.other='intelligence' and i.y is null and i.status is null Group by substring_index(mname,'楼',1)+0,Place")
    List<Place> getPlacesExceptions();

    @Select("select mname,Place from f_light_demo  where other='intelligence' Group by substring_index(mname,'楼',1)+0,Place")
    List<Place> getPlaces();

}
