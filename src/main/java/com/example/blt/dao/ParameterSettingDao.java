package com.example.blt.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-18 10:52
 **/
@Mapper
public interface ParameterSettingDao {

    @Select("select count(*) from t_parameter_setting")
    Integer getParameterSettingCount();

    @Insert("INSERT INTO t_parameter_setting(project,scene_count,unit) value(#{project},#{sceneCount},#{unit})")
    void saveParameterSetting(@Param("project") String project, @Param("sceneCount") int sceneCount, @Param("unit") String unit);
}
