package com.example.blt.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select unit,scene_count as sceneCount from t_parameter_setting where project=#{project}")
    Map<String,Object> getParameterSetting(String project);

    List<Map<String,Object>> getHost(String meshId);

//    @Select("")
//    String getUnitName(String project);

    @Select("select id,mesh_name as name from t_mesh")
    List<Map<String,Object>> getMeshs();

    @Select("select id,place_name as name from t_eplace")
    List<Map<String,Object>> getPlaces();

    @Select("select id,gname as name from t_egroup")
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
}
