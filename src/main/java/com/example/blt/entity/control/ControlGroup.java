package com.example.blt.entity.control;

import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-04 11:23
 **/
public class ControlGroup {

    private Integer id;
    private String gname;
    private List<ControlMesh> controlMeshes;

    public List<ControlMesh> getControlMeshes() {
        return controlMeshes;
    }

    public void setControlMeshes(List<ControlMesh> controlMeshes) {
        this.controlMeshes = controlMeshes;
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

    @Override
    public String toString() {
        return "ControlGroup{" +
                "id=" + id +
                ", gname='" + gname + '\'' +
                ", controlMeshes=" + controlMeshes +
                '}';
    }
}
