package com.example.blt.dao;

import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.control.ControlHost;
import com.example.blt.entity.control.ControlMesh;
import com.example.blt.entity.control.GroupList;
import com.example.blt.entity.control.MeshList;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-03 14:15
 **/
@Mapper
public interface ControlCenterDao {

    @Select("select id,tname,tid,mesh_id as meshId ,IF(repetition = '0', '不重复', '重复') repetition ,dayObj,IF( item_set = '1', '启用', '停用' ) itemSet,week from f_time_line WHERE mesh_id=#{meshId} order by tid")
    List<TimeLine> getTimeLinesByMeshId(@Param("meshId") String meshId);

    @Select("SELECT tsid,concat(IF( LENGTH( hour ) = 1, CONCAT( '0', hour ), hour ),':',IF( LENGTH( minute ) = 1, concat( '0', minute ), minute ) ) AS time, scene_id  FROM f_time_point WHERE tsid = #{tsid} order by time")
    List<TimePoint> getTimePointByTsid(@Param("tsid") Integer tsid);

    List<ControlMesh> getControlGroups();

    @Insert("INSERT INTO t_group(gname,create_date,update_date) VALUES(#{gname},now(),now())")
    void createGroup(@Param("gname") String gname);

    @Select("select count(*) from t_group where gname=#{gname}")
    Integer getGname(String gname);

    @Update("update t_group set gname=#{gname} where id=#{id}")
    void renameGroup(@Param("gname") String gname,@Param("id") Integer id);

    @Select("select id,gname from t_group")
    List<GroupList> getGroups();

    @Select("select count(*) from t_master_subordinate where mname=#{mname}")
    Integer getMname(@Param("mname") String mname);

    @Update("update t_master_subordinate set mname=#{mname} where mesh_id=#{meshId}")
    void renameMesh(@Param("mname")String mname, @Param("meshId")String meshId);

    @Select("select count(*) FROM t_host_info_copy1 WHERE other=#{pname}")
    Integer getPname(@Param("pname") String pname);

    @Update("update t_host_info_copy1 set other=#{pname} where mac=#{mac}")
    void renamePname(@Param("pname") String pname, @Param("mac") String mac);

    @Delete("DELETE FROM t_host_info_copy1 where mac=#{mac}")
    void deleteHost(@Param("mac") String mac);

    @Delete("DELETE FROM t_group where id=#{id}")
    void deleteGroup(@Param("id") Integer id);

    @Update("update t_master_subordinate set master_id=1 where mesh_id=#{meshId}")
    void updatetMaster(@Param("meshId") String meshId);

    @Update("update t_host_info_copy1 set is_master=1 where mesh_id=#{meshId} and is_master=#{type}")
    void updateHostInfo(@Param("meshId")String meshId,@Param("type") String type);

    @Select("select id,mesh_id as meshId,ifnull(mname,mesh_id) as mname from t_master_subordinate")
    List<MeshList> getMeshs();

    List<ControlMesh> getControlGroupsByGname(@Param("gname") String gname);

    List<ControlMesh> getControlGroupsByAllGroup(String gname);

    @Update("update t_master_subordinate set gid=(select id from t_group where gname=#{gname}) where mesh_id=#{meshId}")
    void selectGroup(@Param("gname")String gname, @Param("meshId")String meshId);

    @Select("select if(status='1','在线','离线') AS state,ifnull( other, mac ) AS pname, mac from t_host_info_copy1 where mesh_id=#{meshId}")
    List<ControlHost> getPanels(@Param("meshId")String meshId);
}
