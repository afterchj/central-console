package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/10/29 10:24
 */
public enum  CommandDict {
    //77011465FFFFFFFF2000000000C1323200000000000001CCCC group
    //77011465FFFFFFFF20000000004200000000000003CCCC scene
    //77011465FFFFFFFF2A00000000C000373700000000CCCC mesh
    MESH_ON("meshOn","77011465FFFFFFFF2A00000000C000373700000000CCCC"),
    MESH_OFF("meshOff","77011465FFFFFFFF2A00000000C000323200000000CCCC"),
    GROUP_OFF("groupOff","77011465FFFFFFFF2000000000C13232000000000000"),
    GROUP_ON("groupOn","77011465FFFFFFFF2000000000C13737000000000000"),
    SCENE("scene","77011465FFFFFFFF200000000042000000000000");

    private String name;
    private String cmd;

    CommandDict(String name,String cmd){
        this.name=name;
        this.cmd=cmd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
