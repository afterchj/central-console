package com.example.blt;

import com.alibaba.fastjson.JSON;
import com.example.blt.entity.dd.Groups;
import com.example.blt.netty.ClientMain;
import com.example.blt.utils.SpringUtils;
import com.example.blt.utils.StringBuildUtils;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongjian.chen
 * @date 2019/6/27 16:24
 */
public class StringBuildUtilsTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static final ClientMain CLIENT_MAIN = new ClientMain();
    @Test
    public void test() {
//        System.out.println("77 05 07 05 22 00 FF 7F C5 EC CC CC".replace(" ","").length());
        String msg = "770509010201000007000302CCCC770507052100FF7FC5ECCCCC770505060B1A010CCCCC";
        String[] array = msg.split("CCCC");
        for (String str : array) {
            System.out.println("str=" + str);
        }
        String strHex = Integer.toHexString(45408);
        String str = "C770505060B1A010";
        if (str.indexOf("C")==0) {
            if (str.length() > 16) {
                str = str.substring(str.length() - 16);
            }else {
                str=str.substring(1)+"C";
            }
        }
        System.out.println("format="+str);
//        String mac=StringBuildUtils.buildMac("90e3");
//        mac=StringBuildUtils.sortMac(mac).replace(":", "");
        String temp = StringBuildUtils.sortMac(str.substring(8, 12)).replace(":", "");
        int product = Integer.parseInt(temp, 16);
        String version = StringBuildUtils.parseHixToStr(str.substring(12));
        System.out.println(strHex + "\t" + temp + "\t" + product + "\t" + version);
//        String msg="77 05 07 05 22 00 FF 7F C5 EC CC CC".replace(" ","");
//        while (str.length() < 20) {
////            str += "C";
////        }
////        String[] array = msg.split("CCCC");
////        while (array[0].length() < 20) {
////            array[0] += "C";
////        }
////        System.out.println(array[0]);
////        System.out.println(JSON.toJSONString(array));
//        String[] array = "77011365FFFFFFFF210D000000521FEA62D7ACF00101CCCC".split("CCCC");
//        System.out.println("array=" + array[0] + "\t" + "770509010908040207070801CCCC77050705791000D7ACF0CCCC".length());
//        String key = String.format("task_%s_%s", "88888888", 1);
//        System.out.println(key);
//        String mac = StringBuildUtils.sortMac("7705070504456DD7ACF0CCCC".substring(8, 20));
//        System.out.println(mac + "\t" + "770509010908040207070801CCCC77050705791000D7ACF0CCCC".indexOf("77050705"));
//        String space = "77 01 12 65 FF FF FF FF 2A 05 00 00 00 C0 00 37 37 00 00 00 00 CC CC ".replace(" ", "");
//        String mobile = "77 01 13 65 FF FF FF FF 21 0D 00 00 00 52 1F EA 62 D7 AC F0 01 01 CC CC ".replace(" ", "");
//        String ping = "77 01 0A 65 FF FF FF FF FE 00 00 00 00 CC CC".replace(" ", "");
//        logger.warn("ping=" + ping + ",len=" + ping.length());
//        logger.warn("mobile=" + mobile + ",len=" + mobile.length());
//        //        String str = "77 04 0F 01 A9 10 64 D7 AC F0 7D 00 00 00 44 4F 03 0A CC CC ".replace(" ","");
//        String c1 = "77 04 10 02 20 9D 01 00 00 C1 32 32 00 00 00 00 00 00 02 1E".replace(" ", "");
//        String c0 = "77 04 0E 02 2A 9D 01 00 00 C0 00 37 37 00 00 00 00 09".replace(" ", "");
//        String c4 = "77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F".replace(" ", "");
//        String c71 = "77 04 0F 02 27 35 00 00 00 71 00 13 00 00 00 00 00 00 0E".replace(" ", "");
//        String c52 = "77 04 10 02 21 69 00 00 00 52 77 65 65 D7 AC F0 00 01 00 85".replace(" ", "");//7704100221F505000052456365D7ACF0000200CCCC
//        String c42 = "77 04 0E 02 20 9D 01 00 00 42 00 00 00 00 00 00 02 83".replace(" ", "");
//        String type = "770505060b1a0109CCCC";
//        String temp = type.substring(8, 12);
//        StringBuildUtils.sortMac(temp).replace(":", "");
//        System.out.println("type=" + StringBuildUtils.sortMac(type.substring(8, 12)).replace(":", ""));
//        String str = "77040E020103000000C000373700000000CC";
//        String CS = "77011265FFFFFFFF20010000004200000000000004CCCC";
//        String C0 = "77011265FFFFFFFF2A01000000C000373700000000CCCC";
//        String pinStrA = "77011366FFFFFFFF04456DD7ACF09D01000008200102CCCC";
//        String pinStrB = "77011365FFFFFFFF2755010000711413000000000000CCCC";
//        int len = CS.length();
//        System.out.println(CS.substring(len - 6, len - 4) + "\t" + "77011265FFFFFFFF".length() + "\t" + "77011265FFFFFFFF2001000000".length());
//        StringBuildUtils.buildLightInfo("127.0.0.1",CS);
//        StringBuildUtils.buildLightInfo("127.0.0.1",C0);
//        StringBuildUtils.tempFormat(str,"127.0.0.1");
//        System.out.println(pinStrA.substring(16) + "\t" + pinStrB.length());
//        String str = "F0ACD7009501".toLowerCase();
//        System.out.println("bufferStr=" + StringBuildUtils.buildMac(str));
//        String str1 = "77040F01A91064D7ACF07D000000444F030ACCCC";
//        String str2 = "77040F022769000000710032000000000000CC";
    }

    @Test
    public void testUUID() {
        System.out.println(Integer.parseInt("D", 16));
        String arg1 = "770509010200010904080306CCCC";
        int len = arg1.length();
        String meshId = "";
        for (int i = 0; i < 16; i += 2) {
            meshId += String.valueOf(Integer.parseInt(arg1.substring(len - 20, len - 4).substring(i, i + 2), 16));
        }
        char[] chars = "0200010904080306".toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 != 0) {
                buffer.append(chars[i]);
            }
        }
        System.out.println(meshId + "\t" + buffer.toString());
//        String arg1 = "770509010200010904080306CCCC";
//        String meshId = arg1.substring(arg1.length() - 20, arg1.length() - 4).replace("0", "");
//        System.out.println("meshId=" + meshId);
//        Map map = new ConcurrentHashMap();
//        map.put("ip", "127.0.0.1");
//        if (StringUtils.isNotBlank(meshId)) {
//            map.put("meshId", meshId);
//        }
//        sqlSessionTemplate.insert("console.saveUpdateHost", map);
//        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void testStr() {
        String strs = "7704100221F505000052456365D7ACF0000200CCCC";
        System.out.println(strs.indexOf("770101"));
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
                    CLIENT_MAIN.sendCron(Groups.GROUPSA.getOn());
                } else if ("02".equals(flag)) {
                    CLIENT_MAIN.sendCron(Groups.GROUPSA.getOff());
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

    @Test
    public void testA() {
//        System.out.println(Integer.parseInt("0a", 16));
        System.out.println("770505060B1A0109".length());
        String on = "77020315323266";
//        System.out.println(on.replace("02", "01"));
        String strs = "77011366FFFFFFFFEC436DD7ACF00100000008200102";
        System.out.println(strs.length());
//        System.out.println(strs.length() + "\t" + strs.indexOf("770101"));
//        System.out.println("77040F0227FD020000713232000000000000CC".length());
//        System.out.println("77040F0152406DD7ACF06900000008200102CCCC".length() + "\t" + "77040F0227A9000000710013000000000000CCCC".length());
        String strA = "77040F0152406DD7ACF06900000008200102CCCC77040F011D406DD7ACF06902000008200102CCCC77040F015E3F6DD7ACF06901000008200102CCCC77040F019B446DD7ACF02900000008200102CCCC77040F01BF406DD7ACF00900000008200102CCCC77040F010D3F6DD7ACF0A900000008200102CCCC77040F01103F6DD7ACF0E900000008200102CCCC";
        String strB = "77040F0227A9000000710013000000000000CCCC77040F022719000000710013000000000000CCCC77040F0227A9010000710013000000000000CCCC77040F0227E9000000710013000000000000CCCC77040F0227D9000000710013000000000000CCCC77040F022739000000710013000000000000CCCC77040F022759000000710013000000000000CCCC77040F0227A9020000710013000000000000CCCC77040F022799000000710013000000000000CCCC77040F0227F9000000710013000000000000CCCC77040F0227A9030000710013000000000000CCCC";
        String demo = "0108020701060302040602560109CCCC77050107CCCC7705020803CCCC770503040200CCCC77011366FFFFFFFFEC436DD7ACF00100000008200102CCCC77011366FFFFFFFFCC436DD7ACF00900000008200102CCCC";
        String[] array = demo.split("CCCC");
        for (String str : array) {
            System.out.println("str=" + str);
        }
//        System.out.println("len=" + strA.length());
//        System.out.println("len=" + strB.length());
//        String[] arrA = strA.split("CCCC");
//        String[] arrB = strB.split("CCCC");
//        String[] arr = strs.split("CCCC");
//        System.out.println(arr[0].length() + "\t" + arr[0]);
//        System.out.println(arrA.length + "\t" + arrA[0]);
//        System.out.println(arrB.length + "\t" + arrB[0]);
    }

    @Test
    public void testMap() {
        Map map = new ConcurrentHashMap();
        map.put("host", "1270.0.01");
        map.put("status", 0);
        System.out.println(map.containsKey("ctype"));

    }
}
