package com.example.blt;

import com.alibaba.fastjson.JSON;
import com.example.blt.entity.dd.Groups;
import com.example.blt.netty.ClientMain;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hongjian.chen
 * @date 2019/6/27 16:24
 */
public class StrUtilTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ClientMain clientMain = new ClientMain();

    @Test
    public void test() {
        //        String str = "77 04 0F 01 A9 10 64 D7 AC F0 7D 00 00 00 44 4F 03 0A CC CC ".replace(" ","");
        String c1 = "77 04 10 02 20 9D 01 00 00 C1 32 32 00 00 00 00 00 00 02 1E".replace(" ", "");
        String c0 = "77 04 0E 02 2A 9D 01 00 00 C0 00 37 37 00 00 00 00 09".replace(" ", "");
        String c4 = "77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F".replace(" ", "");
        String c71 = "77 04 0F 02 27 35 00 00 00 71 00 13 00 00 00 00 00 00 0E".replace(" ", "");
        String c52 = "77 04 10 02 21 69 00 00 00 52 77 65 65 D7 AC F0 00 01 00 85".replace(" ", "");//7704100221F505000052456365D7ACF0000200CCCC
        String c42 = "77 04 0E 02 20 9D 01 00 00 42 00 00 00 00 00 00 02 83".replace(" ", "");
        String str = "77040E020103000000C000373700000000CC";
//        StrUtil.buildLightInfo(c52,"127.0.0.1");
//        StrUtil.buildLightInfo(c71,"127.0.0.1");
//        StrUtil.tempFormat(str,"127.0.0.1");
        System.out.println(c71 + "\t" + c71.length());
//        String str = "F0ACD7009501".toLowerCase();
//        System.out.println("bufferStr=" + StrUtil.buildMac(str));
//        String str1 = "77040F01A91064D7ACF07D000000444F030ACCCC";
//        String str2 = "77040F022769000000710032000000000000CC";
//        buildLightInfo(str1,"127.0.0.1");
    }

    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void testStr() {
        String strs = "7704100221F505000052456365D7ACF0000200CCCC";
        String str = strs.substring(18);
        int len = str.length();
        String prefix = str.substring(0, 2).toUpperCase();
        String tmp = str.substring(2, 4);
        String cid = str.substring(len - 4, len - 2);
        Map map = new HashMap();
        switch (prefix) {
            case "52"://52表示遥控器控制命令，01,02字段固定，01表示开，02表示关
                //7704100221F505000052456365D7ACF0000200CCCC
                String flag = str.substring(len - 6, len - 4);
                if (strs.length() > 40) {
                    flag = str.substring(len - 8, len - 6);
                }
                logger.warn("flag=" + flag);
                if ("01".equals(flag)) {
                    clientMain.sendCron(Groups.GROUPSA.getOn());
                } else if ("02".equals(flag)) {
                    clientMain.sendCron(Groups.GROUPSA.getOff());
                }
                map.put("ctype", prefix);
                map.put("cid", flag);
                break;
            case "C0"://pad或手机，C0代表全控，37 37字段是x、y值
                map.put("ctype", prefix);
                map.put("x", str.substring(4, 6));
                map.put("y", str.substring(6, 8));
                break;
            case "42": //42代表场景控制，02字段是场景ID
                map.put("ctype", prefix);
                map.put("cid", cid);
                break;
            case "CA":
                //门磁,77 04 0E 02 20 9D 01 00 00 CA 00  关门,77 04 0E 02 20 9D 01 00 00 CA 01   开门
                map.put("ctype", prefix);
                map.put("x", tmp);
                break;
            case "CB":
//                人感 ,77 04 0E 02 20 9D 01 00 00 CB 00  无人,77 04 0E 02 20 9D 01 00 00 CB 01  有人
                map.put("ctype", "CB");
                map.put("x", tmp);
                break;
            case "CC":
//                温湿度 77 04 0E 02 20 9D 01 00 00 CC, 温度 00 00 湿度  00 00
                map.put("ctype", prefix);
                map.put("cid", tmp);
                map.put("x", str.substring(4, 8));
                map.put("y", str.substring(8, 12));
                break;
            default:
                //C1代表组控，32 32字段是x、y值, 02字段是组ID
//               C4，RGB组控77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F
//                灯状态信息77 04 0F 02 27 35 00 00 00 71 00 13 00 00 00 00 00 00 0E
                if ("C1".equals(prefix) || "C4".equals(prefix) || "71".equals(prefix)) {
                    map.put("ctype", prefix);
                    map.put("x", str.substring(2, 4));
                    map.put("y", str.substring(4, 6));
                    map.put("cid", cid);
                }
                break;
        }
        logger.warn(strs.length() + "\n" + JSON.toJSONString(map));
    }
}
