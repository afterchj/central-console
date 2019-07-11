package com.example.blt.dao;

import com.example.blt.entity.CommandLight;
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


    @Select("select cid,ctype,x,y from t_command_info where ctype is not null and SUBSTRING_INDEX(SUBSTRING_INDEX(host,'.',-2),'.',1)=#{host} order by id desc limit 1")
    CommandLight getCommandInfo(@Param("host") String host);

    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info_copy1 i where d.lmac=i.lmac and  (i.y is not null)and d.other='intelligence' Group by mname")
    List<Map<String,Object>> getIntelligenceCenterLNum();//每一层正常状态的灯个数


    @Select("select count(*) as PlaceLNum,d.mname,d.place from f_light_demo d,t_light_info_copy1 i where d.lmac=i.lmac and (i.y is not null) and d.other='intelligence' Group by mname,place")
    List<LightDemo> getIntelligencePlaceLNum();//每一层每个区域的正常状态灯个数


    @Select("SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0'  when i.y is null and i.status  ='0' then '1' when i.y is null and i.status ='1' then '0'  when i.y is null and i.status is null then null  END AS status,d.place,d.Group,CONCAT((100-i.y*5),'%') AS y  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info_copy1 Group by lmac) i ON d.lmac = i.lmac where d.other='intelligence' ORDER BY d.mname,d.group")
    List<LightDemo> getIntelligenceLightInfo();

    @Select("select mname,count(*) as PlaceLNum,place from f_light_demo where other =#{other} Group by mname,place ORDER BY mname,place")
    List<LightDemo> getPlaceLNum(@Param("other") String other);//每个楼层每个区域的灯个数

    @Select("select count(*) as centerLNum,mname from f_light_demo d where d.other=#{other} Group by mname")
    List<LightDemo> getCenterLNum(@Param("other") String other);//每个楼层的灯个数


    @Select("select  lmac ,mname,lname,#{status} as status,d.place,d.group from f_light_demo d where other ='intelligence' ORDER BY" +
            " mname,d.group")
    List<LightDemo>  getMonitorFromRemoteByStatus(@Param("status") String status,@Param("other") String other);//全开全关

    @Select("select  lmac ,mname,lname,#{status} as status,d.place,d.group from f_light_demo d where other =#{other} and" +
            " d.group=#{group} ORDER BY mname,lname")
    List<LightDemo> getMonitorFromPhoneByGroup(@Param("group") int groupId, @Param("status") String status,@Param("other") String other);//单组全开全关

}
