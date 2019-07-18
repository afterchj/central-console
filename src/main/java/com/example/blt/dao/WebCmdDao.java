package com.example.blt.dao;

import com.example.blt.entity.CenterException;
import com.example.blt.entity.CommandLight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-15 14:07
 **/
@Mapper
public interface WebCmdDao {

    @Select("select cid,host,y from t_command_info where ctype is not null and SUBSTRING_INDEX(SUBSTRING_INDEX(host,'.',-2),'.',1)='10' and ctype='CW' order by id desc limit 3")
    List<CommandLight> getWebCmd();

    @Select("select mname from f_light_demo d left join t_light_info i on d.lmac=i.lmac where d.other='intelligence' and i.y is null and i.status is null group by substring_index(mname,'楼',1)+0")
    List<String> getException();

    @Select("select mname from f_light_demo d left join t_light_info i on d.lmac=i.lmac where d.other='intelligence' and i.y is null and i.status is null and mname=#{mname} and place=#{place} and groupId !=#{groupId} group by substring_index(mname,'楼',1)+0")
    List<String> getExceptionByGroupId(@Param("groupId") int groupId,@Param("mname") String mname,@Param("place") int
            place);

    @Select("select mname from f_light_demo d left join t_light_info i on d.lmac=i.lmac where d.other='intelligence' and i.y is null and i.status is null and mname=#{mname} and place!=#{place} group by substring_index(mname,'楼',1)+0")
    List<String> getExceptionByPlace(@Param("mname") String mname,@Param("place") int
            place);

    @Select("select i.mname from (select count(*) as count,mname,place,groupId from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' group by substring_index(mname,'楼',1)+0 ,place,groupId  having count(*)<4) i group by substring_index(i.mname,'楼',1)+0")
    List<String> getDiff();

//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' group by substring_index(mname,'楼',1)+0 ,place,groupId  having count(*)<4")
//    List<String> getDiffs();
//
//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' and i.y is null group by substring_index(mname,'楼',1)+0 ,place,groupId")
//    List<String> getExceptions();
//
    @Select("select mname,place,groupId from f_light_demo where other='intelligence' group by substring_index(mname,'楼',1)+0,place,groupId")
    List<CenterException> getMnames();
//
//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' and d.mname!=#{mname} group by substring_index(mname,'楼',1)+0 ,place,groupId  having count(*)<4")
//    List<String> getDiffsByMname(@Param("mname")String mname);
//
//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' and i.y is null and d.mname!=#{mname} group by substring_index(mname,'楼',1)+0 ,place,groupId")
//    List<String> getExceptionsByMname(@Param("mname")String mname);
//
//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' and d.mname=#{mname} and d.groupId!=#{groupId} group by substring_index(mname,'楼',1)+0 ,place,groupId  having count(*)<4")
//    List<String> getDiffsByMnameAndGroupId(@Param("mname")String mname, @Param("groupId") int groupId);
//
//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' and i.y is null and d.mname=#{mname} and d.groupId!=#{groupId} group by substring_index(mname,'楼',1)+0 ,place,groupId")
//    List<String> getExceptionsByMnameAndGroupId(@Param("mname")String mname, @Param("groupId")int groupId);
//
//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' and d.mname=#{mname} and d.place!=#{place} group by substring_index(mname,'楼',1)+0 ,place,groupId  having count(*)<4")
//    List<String> getDiffsByMnameAndPlace(@Param("mname") String mname, @Param("place") int place);
//
//    @Select("select mname from f_light_demo d,t_light_info i where d.lmac=i.lmac and d.other='intelligence' and i.y is null and d.mname=#{mname} and d.place!=#{place} group by substring_index(mname,'楼',1)+0 ,place,groupId")
//    List<String> getExceptionsByMnameAndPlace(@Param("mname") String mname, @Param("place") int place);
}
