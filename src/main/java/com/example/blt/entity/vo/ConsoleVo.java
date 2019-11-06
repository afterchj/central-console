package com.example.blt.entity.vo;

/**
 * Created by hongjian.chen on 2019/6/14.
 */
public class ConsoleVo {

    private String command;
    private String host;
    private String type;
    private Boolean status;
    private Boolean is_master;
    private Boolean is_control;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIs_master() {
        return is_master;
    }

    public void setIs_master(Boolean is_master) {
        this.is_master = is_master;
    }

    public Boolean getIs_control() {
        return is_control;
    }

    public void setIs_control(Boolean is_control) {
        this.is_control = is_control;
    }
}
