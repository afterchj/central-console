package com.example.blt.dao;

import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("SELECT tsid,concat(IF( LENGTH( hour ) = 1, CONCAT( '0', hour ), hour ),':',IF( LENGTH( minute ) = 1, concat( '0', minute ), minute ) ) AS time,CASE scene_id WHEN 21 THEN 'ALL OFF' WHEN 22 THEN 'ALL ON' ELSE CONCAT( '场景', scene_id ) END AS sname FROM f_time_point WHERE tsid = #{tsid} order by time")
    List<TimePoint> getTimePointByTsid(@Param("tsid") Integer tsid);
}
