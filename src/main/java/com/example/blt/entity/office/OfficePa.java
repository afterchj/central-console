package com.example.blt.entity.office;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 14:52
 **/
public class OfficePa {

    private String x;
    private String y;
    private int[] unitArray;
    private String operation;
    private String project;
    private Integer sceneId;

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public int[] getUnitArray() {
        return unitArray;
    }

    public void setUnitArray(int[] unitArray) {
        this.unitArray = unitArray;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
