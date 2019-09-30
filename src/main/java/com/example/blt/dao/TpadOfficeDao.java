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

    @Select("select id from t_host_info where id=(select h.hid from t_mesh m,t_host_mesh h where m.id=h.mid and m.mesh_id=(select mesh_id from t_mesh_setting where project='taptOffice')) and status=1")
    String getHostId(@Param("project")String projectName);

    @Select("select count ,name from t_parameter_setting")
    List<Map<String,Object>> getParameterSetting();
}
