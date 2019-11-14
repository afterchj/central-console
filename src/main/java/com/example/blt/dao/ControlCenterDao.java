package com.example.blt.dao;

import com.example.blt.entity.PlaceInfo;
import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.control.ControlHost;
import com.example.blt.entity.control.ControlMaster;
import com.example.blt.entity.control.GroupList;
import com.example.blt.entity.control.MeshList;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-03 14:15
 **/
@Mapper
public interface ControlCenterDao {

    List<TimeLine> getTimeLinesByMeshId(@Param("meshId") String meshId);

    List<TimePoint> getTimePointByTsid(@Param("tsid") Integer tsid);

    List<ControlMaster> getControlGroups(@Param("gid") Integer gid,@Param("meshId") String meshId);

    @Insert("INSERT INTO t_mgroup(gname,create_date,update_date) VALUES(#{gname},now(),now())")
    void createGroup(@Param("gname") String gname);

    @Select("select count(*) from t_mgroup where gname=#{gname}")
    Integer getGname(String gname);

    @Update("update t_mgroup set gname=#{gname} where id=#{id}")
    void renameGroup(@Param("gname") String gname, @Param("id") Integer id);

    @Select("select id,gname,group_id as groupId from t_mgroup")
    List<GroupList> getGroups();

    @Select("select count(*) from t_mesh where mesh_name=#{mname}")
    Integer getMname(@Param("mname") String mname);

    @Update("update t_mesh set mesh_name=#{mname} where mesh_id=#{meshId}")
    void renameMesh(@Param("mname") String mname, @Param("meshId") String meshId);

    @Select("select count(*) FROM t_host_info WHERE name=#{pname}")
    Integer getPname(@Param("pname") String pname);

    @Update("update t_host_info set name=#{pname} where id=#{id}")
    void renamePname(@Param("pname") String pname, @Param("id") int id);

    @Delete("DELETE FROM t_host_info where id=#{id}")
    void deleteHost(@Param("id") int id);

    @Delete("DELETE FROM t_mgroup where id=#{id}")
    void deleteGroup(@Param("id") Integer id);

    @Update("update t_host_info set is_master=#{type} where id in(select hm.hid from t_host_mesh hm,t_mesh m where m.id=hm.mid and m.mesh_id=#{meshId})")
    void updateHostInfo(@Param("meshId") String meshId, @Param("type") int type);

    @Select("select id,mesh_id as meshId,ifnull(mesh_name,mesh_id) as mname ,flag from t_mesh")
    List<MeshList> getMeshs();

    @Update("replace into t_mesh_group (gid,mid) VALUES(#{gid},(select id from t_mesh where mesh_id=#{meshId}))")
    void selectGroup(@Param("gid") Integer id, @Param("meshId") String meshId);

    List<ControlHost> getPanels(@Param("meshId") String meshId);

    @Delete("DELETE FROM t_mesh where mesh_id=#{meshId}")
    void deleteMesh(@Param("meshId") String meshId);

    @Delete("TRUNCATE TABLE f_time_line")
    void reSetTimeLine();

    @Delete("TRUNCATE TABLE f_time_point")
    void reSetTimePoint();

    @Delete("TRUNCATE TABLE t_mgroup")
    void reSetGroup();

    @Delete("TRUNCATE TABLE t_host_info")
    void reSetHostInfo();

    @Delete("TRUNCATE TABLE t_host_mesh")
    void reSetHostMesh();

    @Delete("TRUNCATE TABLE t_mesh")
    void reSetMesh();

    @Delete("TRUNCATE TABLE t_mesh_group")
    void reSetMeshGroup();

    @Delete("TRUNCATE TABLE t_cron")
    void reSetCron();

    List<Map<String,Object>> getPOEState(@Param("meshId")String meshId);

    @Delete("delete FROM t_mesh_group where gid=#{gid}")
    void deleteMeshGroupByGid(@Param("gid") Integer id);

    @Delete("DELETE FROM t_mesh_group WHERE mid=(select id from t_mesh where mesh_id=#{meshId})")
    void deleteMeshGroup(@Param("meshId")String meshId);

    @Delete("DELETE FROM t_host_mesh where hid=#{hid}")
    void deleteHostMesh(@Param("hid") int id);

    @Delete("DELETE FROM t_host_info where id in(select hm.hid from t_host_mesh hm,t_mesh m where m.id=hm.mid and m.mesh_id=#{meshId})")
    void deleteHostByMeshId(String meshId);

    @Delete("DELETE FROM t_host_mesh where mid=(select id from t_mesh where mesh_id=#{meshId})")
    void deleteHostMeshByMeshId(String meshId);

    @Select("select count(*) from t_mgroup")
    Integer getGroupCount();

    @Select("select flag from t_mesh where mesh_id=#{meshId}")
    String getMeshState(String meshId);

    @Select("select id,place_id as placeId,pname from  t_mplace")
    List<PlaceInfo> getPlaces();
}
