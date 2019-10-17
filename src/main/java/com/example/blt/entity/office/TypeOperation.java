package com.example.blt.entity.office;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 15:05
 **/
public enum  TypeOperation {

    GROUP("group"),SCENE("scene"),PLACE("place"),MESH("mesh"),ACCEPT_ID("acceptID"),OTA("ota")
    , ON_OFF("onOff"),DIMMING("dimming")
    , GROUP_ON_OFF_START("77010416"), GROUP_ON_END("373766"),GROUP_OFF_END("323266"),
    ON("37"),OFF("32"),CMD_END("66")
    , DIMMING_CMD_START("77010315"), SCENE_CMD_START("77010219")
    , MESH_ON_CMD("77010315373766"),MESH_OFF_CMD("77010315323266"), MESH_ON_OFF_CMD_START("77010315")
    , GROUP_START("77011465FFFFFFFF2A00000000C1"),GROUP_BETWEEN("000000000000"),END("CCCC");
//    77011465FFFFFFFF2A00000000C000373700000000CCCC
//    77011465FFFFFFFF2A00000000C000323200000000CCCC
//    77011465FFFFFFFF2A00000000C1373700000000000002CCCC
//    77011465FFFFFFFF2A00000000C1373700000000000001CCCC
//    77011465FFFFFFFF20000000004200000000000003CCCC

    private String key;

    TypeOperation(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static void main(String[] args) {
        String str = TypeOperation.DIMMING.getKey();
        System.out.println(str);
    }

    public static TypeOperation getType(String dataTypeCode){
        for(TypeOperation enums:TypeOperation.values()){
            if(enums.key.equals(dataTypeCode)){
                return enums;
            }
        }
        return null;
    }
}
