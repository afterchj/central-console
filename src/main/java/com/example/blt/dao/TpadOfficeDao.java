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

    @Select("select count ,name from t_parameter_setting")
    List<Map<String,Object>> getParameterSetting();

    @Select("select mesh_id as meshId ,id from t_mesh_setting where project=#{project}")
    List<Map<String,Object>> getMesh(String projectName);

    @Select("select host_id as hostId,is_master as master from t_host_info where id=(select h.hid from t_mesh m,t_host_mesh h where m.id=h.mid and m.mesh_id=#{meshId}) ")
    List<Map<String,Object>> getHost(String meshId);

    @Select("select unit from t_parameter_setting where project=#{project}")
    String getUnitName(String project);

    @Select("select id,mesh_name as name from t_mesh")
    List<Map<String,Object>> getMeshs();

    @Select("select id,place_name as name from t_eplace")
    List<Map<String,Object>> getPlaces();

    @Select("select id,gname as name from t_egroup")
    List<Map<String,Object>> getGroups();
}
