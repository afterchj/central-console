package com.example.blt.utils;


/**
 * @author hongjian.chen
 * @date 2019/7/4 17:39
 */
public class HexConvert {

    /**
     * @Title:bytesHexToString
     * @Description:16进制字符串转字符串
     * @param src
     * 16进制字符串
     * @return String
     * @throws
     */
    public static String bytesHexToString(byte[] src) {
        StringBuffer sb = new StringBuffer();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
            if (i != src.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    /**
     * @param src 16进制字符串
     * @return 字节数组
     * @throws
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     */
    public static String hexStringToString(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return temp;
    }
    /**
     * @param strPart 字符串
     * @return 16进制字符串
     * @Title:string2HexString
     * @Description:字符串转16进制字符串
     */
    public static String stringToHexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {

        String hex = "3737303130333135333233323636";
        String hex1 = "w\u0004\u000F\u0001zDm\u05EC�\u0001  \u0001\u0002��w\u0004\u000F\u0001�Bm\u05EC�   \u0001\u0002��";
        String str=hexStringToString(hex);
        System.out.println("len="+str.length());
        System.out.println("str=" + str);
        System.out.println(str.length());
        System.out.println("hex=" + stringToHexString(str));
    }

}
