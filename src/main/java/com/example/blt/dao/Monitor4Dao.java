package com.example.blt.dao;

import com.example.blt.entity.CommandLight;
import com.example.blt.entity.HostInfo;
import com.example.blt.entity.LightDemo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by chenhao.lu on 2019/7/8.
 */
@Mapper
public interface Monitor4Dao {


    @Select("select id,cid,ctype,host,x,y from t_command_info where ctype is not null and (SUBSTRING_INDEX(SUBSTRING_INDEX(host,'.',-2),'.',1)='10' or host='all')  order by id desc limit 1")
    CommandLight getCommandInfo(@Param("host") String host);

    @Select("select id,cid,ctype,host,x,y from t_command_info where ctype is not null and (host=#{host} or " +
            "host='all')  order by id desc limit 1")
    CommandLight getCommandInfo2(@Param("host") String host);

    @Select("select count(*) as centerLNum,mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and  (i.y is not null)and d.other='intelligence' Group by substring_index(mname,'楼',1)+0")
    List<Map<String,Object>> getIntelligenceCenterLNum();//每一层正常状态的灯个数


    @Select("select count(*) as PlaceLNum,d.mname,d.Place from f_light_demo d,t_light_info i where d.lmac=i.lmac and (i.y is not null) and d.other='intelligence' Group by substring_index(mname,'楼',1)+0,Place")
    List<LightDemo> getIntelligencePlaceLNum();//每一层每个区域的正常状态灯个数


    @Select("SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0'  when i.y is null and i.status  ='0' then '1' when i.y is null and i.status ='1' then '0'  when i.y is null and i.status is null then null  END AS status,d.Place,d.groupId,CONCAT((100-i.y*5),'%') AS y  FROM f_light_demo_intelligence d  JOIN (select  lmac,x ,y,status from t_light_info) i ON d.lmac = i.lmac ")
    List<LightDemo> getIntelligenceLightInfo();

    @Select("select mname,count(*) as PlaceLNum,Place from f_light_demo where other =#{other} Group by mname,Place ORDER BY substring_index(mname,'楼',1)+0,Place")
    List<LightDemo> getPlaceLNum(@Param("other") String other);//每个楼层每个区域的灯个数

    @Select("select count(*) as centerLNum,mname from f_light_demo d where d.other=#{other} Group by mname")
    List<LightDemo> getCenterLNum(@Param("other") String other);//每个楼层的灯个数


    @Select("select  lmac ,mname,lname,#{status} as status,d.Place,d.groupId from f_light_demo d where other ='intelligence' ORDER BY" +
            " mname,d.groupId")
    List<LightDemo>  getMonitorFromRemoteByStatus(@Param("status") String status,@Param("other") String other);//全开全关

    @Select("select  lmac ,mname,lname,#{status} as status,d.Place,d.groupId from f_light_demo d where other =#{other} and" +
            " d.groupId=#{groupId} ORDER BY mname,lname")
    List<LightDemo> getMonitorFromPhoneByGroup(@Param("groupId") int groupId, @Param("status") String status,@Param("other") String other);//单组全开全关

    @Select("select Place from f_light_demo ld where ld.mname=#{mname} and ld.groupId=#{groupId} limit 1")
    Integer getPlace(@Param("mname") String mname,@Param("groupId") int groupId);

    @Select("SELECT a.status FROM (SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and i.y is not null THEN '0'  when i.y is null and i.status  ='0' then '1' when i.y is null and i.status ='1' then '0'  when i.y is null and i.status is null then null  END AS status,d.Place,d.groupId,CONCAT((100-i.y*5),'%') AS y  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info Group by lmac) i ON d.lmac = i.lmac where d.other='intelligence' and d.mname=#{mname} and d.groupId != #{groupId} and Place= #{Place}) a")
    List<Integer> getStatusOfPlace(@Param("mname") String mname,@Param("Place") int place,@Param("groupId") int groupId);

    @Select("SELECT a.status FROM (SELECT d.lmac ,d.mname,d.lname ,CASE WHEN i.y = '32' THEN '1' WHEN i.y != '32' and" +
            " i.y is not " +
            "null THEN '0'  when i.y is null and i.status  ='0' then '1' when i.y is null and i.status ='1' then '0'  when i.y is null and i.status is null then null  END AS status,d.Place,d.groupId,CONCAT((100-i.y*5),'%') AS y  FROM f_light_demo d LEFT JOIN (select  lmac,x ,y,status from t_light_info Group by lmac) i ON d.lmac = i.lmac where d.other='intelligence' and d.mname=#{mname} and " +
            "Place !=#{Place}) a")
    List<Integer> getStatusOfFloor(@Param("mname") String mname,@Param("Place") int place,@Param("groupId") int groupId);


    @Insert("insert into f_time_line (tname,tid,mesh_id,repetition,week,dayObj,ischoose,item_desc,item_set,item_tag,create_date,update_date) values (#{tname},#{tid},#{meshId},#{repetition},#{week},#{dayObj},#{ischoose},#{item_desc},#{item_set},#{item_tag},NOW(),NOW())")
    @SelectKey(statement="select last_insert_id()",before=false,keyProperty="id",resultType=Integer.class,keyColumn="id")
    void insertTimeLine(Map<String, Object> map);


    @Insert("insert into f_time_point (tsid,scene_id,hour,minute,light_status,create_date,update_date) values (#{id},#{sceneId}, #{hour},#{minute},#{lightStatus},NOW(),NOW())")
    void insertTimePoint(Map<String, Object> map);

    @Select("select count(*) from f_time_line where mesh_id=#{meshId} and tid=#{tid}")
    int findTimeLine(Map<String, Object> map);

    @Select("select count(*) from t_mesh where mesh_id=#{meshId}")
    int findTMesh(@Param("meshId") String meshId);

    @Insert("insert into t_mesh (mesh_id,mesh_name,log_date) values (#{meshId},#{mname},NOW())")
    void insertTMesh(@Param("meshId") String meshId,@Param("mname") String mname);

    @Update("update t_mesh set mesh_name= #{mname},log_date=NOW() where mesh_id=#{meshId}")
    void updateTMesh(@Param("meshId")String meshId,@Param("mname")String mname);

//    @Update("update t_host_info set mesh_name = #{mname},log_date = NOW() where mesh_id = #{meshId}")
//    void updateHostInfo(@Param("meshId")String meshId, @Param("mname")String mname);

    @Select("select host_id from t_host_info where mesh_id=(select mesh_id from mesh_test where project=#{project}) and status=1")
    String getHostId(@Param("project") String host);

}
