package com.example.blt.utils;

import com.example.blt.entity.office.CmdJoin;
import com.example.blt.entity.office.TypeOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import static com.example.blt.entity.office.TypeOperation.CMD_END;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-10-10 14:09
 **/
@Component
public class JoinCmdUtil {

    public String joinCmd(String type, String x, String y, Integer groupId, Integer sceneId) throws Exception {
        String cmd = null;
        StringBuffer sb;
        TypeOperation typeEnum = TypeOperation.getType(type);
        switch (typeEnum) {
            case MESH:
                if (StringUtils.isNotBlank(x)) {
                    sb = new StringBuffer();
                    cmd = sb.append(TypeOperation.MESH_ON_OFF_CMD_START.getKey())
                            .append(x)
                            .append(y)
                            .append(CMD_END.getKey())
                            .toString();
                }
                break;
            case GROUP:
                sb = new StringBuffer();
                String hexGroupId = String.format("%02x", groupId).toUpperCase();
                cmd = sb.append(TypeOperation.GROUP_ON_OFF_START.getKey())
                        .append(hexGroupId)
                        .append(x)
                        .append(y)
                        .append(CMD_END.getKey())
                        .toString();
                break;
            case SCENE:
                sb = new StringBuffer();
                String HexSceneId = String.format("%02x", sceneId).toUpperCase();
                cmd = sb.append(TypeOperation.SCENE_CMD_START.getKey())
                        .append(HexSceneId)
                        .toString();
                break;
            default:
                throw new Exception("参数错误");
        }
        return cmd;
    }

    public static String joinNewCmd(String type, String x, String y, Integer groupId, Integer sceneId) throws Exception {
        String cmd = null;
        StringBuffer sb;
        TypeOperation typeEnum = TypeOperation.getType(type);
        switch (typeEnum) {
            case MESH:
                if (StringUtils.isNotBlank(x)) {
                    sb = new StringBuffer();
                    cmd = sb.append(CmdJoin.CMD_MESH_START.getKey())
                            .append(x)
                            .append(y)
                            .append(CmdJoin.CMD_MESH_END.getKey())
                            .toString();
                }
                break;
            case GROUP:
                sb = new StringBuffer();
                String hexGroupId = String.format("%02x", groupId).toUpperCase();
                cmd = sb.append(CmdJoin.CMD_GROUP_START.getKey())
                        .append(x)
                        .append(y)
                        .append(CmdJoin.CMD_GROUP_BETWEEN.getKey())
                        .append(hexGroupId)
                        .append(CmdJoin.END.getKey())
                        .toString();
                break;
            case SCENE:
                sb = new StringBuffer();
                String hexSceneId = String.format("%02x", sceneId).toUpperCase();
                cmd = sb.append(CmdJoin.CMD_SCENE_START.getKey())
                        .append(hexSceneId)
                        .append(CmdJoin.END.getKey())
                        .toString();
                break;
            default:
                throw new Exception("参数错误");
        }
        return cmd;
    }
}
