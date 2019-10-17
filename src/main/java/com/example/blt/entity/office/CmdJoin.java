package com.example.blt.entity.office;

/**
 * Created by nannan.li on 2019/10/16.
 */
public enum CmdJoin {

    CMD_MESH_START("77011465FFFFFFFF2000000000C000",""),CMD_MESH_END("00000000CCCC","")
    ,CMD_GROUP_START("77011465FFFFFFFF2000000000C1",""),CMD_GROUP_BETWEEN("000000000000","")
    ,CMD_SCENE_START("77011465FFFFFFFF200000000042000000000000","")
    ,END("CCCC","命令结束符")
    ,CMD_INQUIRY_MESHID("77050101CCCC","询问POE的meshId和密钥")
    ,CMD_INQUIRY_MAC("77050105CCCC","查询POE的MAC地址")
    ,CMD_INQUIRY_VERSION("77050106CCCC","查询POE的固件版本信息")
    ,CMD_INQUIRY_MESH_NODE_NUM("77050104CCCC","询问mesh网络当前节点数量")
    ,CMD_INQUIRY_TO_POE("77050103CCCC","应答询问")
    ,CMD_ACCEPT_ID("7701010466","接收Id")
    ,CMD_OTA("7701010566","OTA");
    //77011465FFFFFFFF2000000000C1323200000000000001CCCC group
    //77011465FFFFFFFF20000000004200000000000003CCCC scene
    //77011465FFFFFFFF2A00000000C000373700000000CCCC mesh

    private String key;
    private String descr;

    CmdJoin(String key, String descr) {
        this.key = key;
        this.descr = descr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
