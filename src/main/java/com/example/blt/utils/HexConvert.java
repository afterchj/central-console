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
//            if (i != src.length - 1) {
//                sb.append(" ");
//            }
        }
        return sb.toString();
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
        String hex = "w\u0004\u000F\u0001�Em\u05EC?- ";
        String str = "7701012766";
        System.out.println("str=" + bytesHexToString(hex.getBytes()));
        System.out.println("hex=" + stringToHexString(str));
    }

}
