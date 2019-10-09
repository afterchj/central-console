package com.example.blt.entity.office;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 14:52
 **/
public class OfficePa {

    private String projectName;
    private String type;
//    private String Operation;
    private String[] arr;
    private String x;
    private String y;
    private String unit;
    private int[] unitArray;
    private String operation;
    private String project;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }
}
