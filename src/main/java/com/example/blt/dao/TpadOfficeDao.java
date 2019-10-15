package com.example.blt.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 11:18
 **/
@Mapper
public interface TpadOfficeDao {

//    @Select("select host_id from t_host_info where id=(select h.hid from t_mesh m,t_host_mesh h where m.id=h.mid and m.mesh_id=(select mesh_id from t_mesh_setting where project='taptOffice')) and status=1")
    @Select("select host_id as hostId,is_master as master from t_host_info where id=(select h.hid from t_mesh m,t_host_mesh h where m.id=h.mid and m.mesh_id=(select mesh_id from t_mesh_setting where project=#{project}))")
    List<Map<String,Object>> getHostId(@Param("project")String projectName);

    @Select("select unit,scene_count as sceneCount,scene_id as sceneId ,x , y, status from t_parameter_setting where project=#{project}")
    Map<String,Object> getParameterSetting(String project);

    List<Map<String,Object>> getHost(String meshId);

//    @Select("")
//    String getUnitName(String project);

    @Select("select id,ifnull(mesh_name,mesh_id) as name, status from t_mesh")
    List<Map<String,Object>> getMeshs();

    @Select("select id,place_name as name, status from t_eplace")
    List<Map<String,Object>> getPlaces();

    @Select("select id,gname as name, status from t_egroup")
    List<Map<String,Object>> getGroups();

    @Select("select mesh_id as meshId from t_mesh where id=(select mid from t_egroup where id=#{id})")
    String getMeshIdByGid(int id);

    @Select("select group_id as groupId from t_egroup where id=#{id}")
    Integer getEGroupId(int id);

    @Select("select mesh_id as meshId from t_mesh where id=#{id}")
    String getMesIdByMid(int id);

    @Select("select mesh_id as meshId from t_mesh where id=(select mid from t_eplace where id=#{id})")
    String getMeshIdByPid(int id);

    @Select("select group_id as groupId from t_egroup where pid=#{id}")
    List<Integer> getGroupIdsByPid(int id);

    Integer getMidByHostId(String hostId);

    @Select("select id from t_egroup where group_id=#{groupId} and mid=#{mid}")
    Integer getEGid(@Param("mid") Integer mid, @Param("groupId") Integer cid);

    @Select("select pid from t_egroup where group_id=#{cid} and mid=#{mid}")
    Integer getEPid(Map<String,Integer> map);

    @Update("update t_parameter_setting set scene_id=#{sceneId} where project=#{project}")
    void updateSceneId(@Param("sceneId") Integer cid,@Param("project") String project);

    @Update("update t_egroup set status=#{status} where mid=#{mid} and group_id=#{cid}")
    @SelectKey(statement="select (select id from t_egroup where mid=#{mid} and group_id=#{cid})id from DUAL",before=true,keyProperty="id",resultType=Integer.class,keyColumn="id")
    void updateEGroupStatus(Map<String,Integer> map);

    @Update("update t_parameter_setting set status=#{status} where project=#{project}")
    void updateStatus(@Param("project") String project, @Param("status") Integer status);

    @Update("update t_parameter_setting set x=#{x},y=#{y} where project=#{project}")
    void updateXY(@Param("project") String project, @Param("x") String x, @Param("y") String y);

    @Update("update t_eplace set status=#{status} where id=#{pid}")
    void updateEPlaceStatus(Map<String, Integer> statusMap);

    @Update("update t_mesh set status=#{status} where id=#{mid}")
    void updateMeshStatus(Map<String, Integer> statusMap);
}
