package com.example.blt;

import com.example.blt.utils.StrUtil;
import org.junit.Test;

/**
 * @author hongjian.chen
 * @date 2019/6/27 16:24
 */
public class StrUtilTest {

    @Test
    public void test(){
        //        String str = "77 04 0F 01 A9 10 64 D7 AC F0 7D 00 00 00 44 4F 03 0A CC CC ".replace(" ","");
        String c1="77 04 10 02 20 9D 01 00 00 C1 32 32 00 00 00 00 00 00 02 1E".replace(" ","");
        String c0="77 04 0E 02 2A 9D 01 00 00 C0 00 37 37 00 00 00 00 09".replace(" ","");
        String c4="77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F".replace(" ","");
        String c71="77 04 0F 02 27 35 00 00 00 71 00 13 00 00 00 00 00 00 0E".replace(" ","");
        String c52="77 04 10 02 21 69 00 00 00 52 77 65 65 D7 AC F0 00 01 00 85".replace(" ","");
        String c42="77 04 0E 02 20 9D 01 00 00 42 00 00 00 00 00 00 02 83".replace(" ","");
//        StrUtil.buildLightInfo(c52,"127.0.0.1");
//        StrUtil.buildLightInfo(c71,"127.0.0.1");
        StrUtil.formatStr(c1,"127.0.0.1");
//        System.out.println(str);
//        String str = "F0ACD7009501".toLowerCase();
//        System.out.println("bufferStr=" + StrUtil.buildMac(str));
//        String str1 = "77040F01A91064D7ACF07D000000444F030ACCCC";
//        String str2 = "77040F022769000000710032000000000000CC";
//        buildLightInfo(str1,"127.0.0.1");
    }
}
