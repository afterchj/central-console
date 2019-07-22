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

    @Select("select mname from f_light_demo d left join t_light_info_copy i on d.lmac=i.lmac where d.other='intelligence' and i.y is null and i.status is null group by substring_index(mname,'楼',1)+0")
    List<String> getException();

    @Select("select i.mname from (select count(*) as count,mname,Place,groupId from f_light_demo d,t_light_info_copy i where d.lmac=i.lmac and d.other='intelligence' group by substring_index(mname,'楼',1)+0 ,Place,groupId  having count(*)<4) i group by substring_index(i.mname,'楼',1)+0")
    List<String> getDiff();

    @Select("select mname,Place,groupId from f_light_demo where other='intelligence' group by substring_index(mname,'楼',1)+0,Place,groupId")
    List<CenterException> getMnames();

    @Select("select mname,Place,groupId from f_light_demo where other='intelligence' and mname='1楼' group by substring_index(mname,'楼',1)+0,Place,groupId")
    List<CenterException> getMnamesByFloor(@Param("floor")String floor);

}
