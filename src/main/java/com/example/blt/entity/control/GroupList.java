package com.example.blt.entity.control;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-04 13:27
 **/
public class GroupList {

    private Integer id;
    private String gname;
    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
}
