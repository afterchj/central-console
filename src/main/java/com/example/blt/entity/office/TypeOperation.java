package com.example.blt.entity.office;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 15:05
 **/
public enum  TypeOperation {

    GROUP("group"),SCENE("scene"),ON_OFF("onOff"),DIMMING("dimming"),MESH("mesh"),GROUP_ON_OFF_START("77010416"),
    GROUP_ON_END("373766"),GROUP_OFF_END("323266"),ON("37"),OFF("37"),DIMMING_CMD_START("77010315"),SCENE_CMD_START
            ("77010219"),MESH_ON_CMD("77010315373766"),MESH_OFF_CMD("77010315323266");

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
