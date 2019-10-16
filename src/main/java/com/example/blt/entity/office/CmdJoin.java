package com.example.blt.entity.office;

/**
 * Created by nannan.li on 2019/10/16.
 */
public enum CmdJoin {

    CMD_MESH_START("77011465FFFFFFFF2000000000C000"),CMD_MESH_END("00000000CCCC")
    ,CMD_GROUP_START("77011465FFFFFFFF2000000000C1"),CMD_GROUP_BETWEEN("000000000000"),
    CMD_SCENE_START("77011465FFFFFFFF200000000042000000000000")
    ,END("CCCC");
    //77011465FFFFFFFF2000000000C1323200000000000001CCCC group
    //77011465FFFFFFFF20000000004200000000000003CCCC scene
    //77011465FFFFFFFF2A00000000C000373700000000CCCC mesh
    private String key;

    CmdJoin(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
