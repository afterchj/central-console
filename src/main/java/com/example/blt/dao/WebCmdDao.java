package com.example.blt.dao;

import com.example.blt.entity.CommandLight;
import org.apache.ibatis.annotations.Mapper;
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

    @Select("select cid,host,y from t_command_info_copy where ctype is not null and SUBSTRING_INDEX(SUBSTRING_INDEX(host,'.',-2),'.',1)='16' and ctype='CW' and log_date BETWEEN  DATE_ADD(now(),INTERVAL -1 minute) and now() order by id desc limit 3")
    List<CommandLight> getWebCmd();
}
