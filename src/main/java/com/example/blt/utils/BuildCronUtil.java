package com.example.blt.utils;

import com.example.blt.entity.dd.CommandDict;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongjian.chen
 * @date 2020/2/25 10:33
 */
public class BuildCronUtil {

    public static String getWeek(String str) {
        if (StringUtils.isEmpty(str)) return null;
        String[] weeks = str.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < weeks.length; i++) {
            switch (weeks[i]) {
                case "周日":
                    weeks[i] = "SUN";
                    break;
                case "周一":
                    weeks[i] = "MON";
                    break;
                case "周二":
                    weeks[i] = "TUE";
                    break;
                case "周三":
                    weeks[i] = "WED";
                    break;
                case "周四":
                    weeks[i] = "THU";
                    break;
                case "周五":
                    weeks[i] = "FRI";
                    break;
                case "周六":
                    weeks[i] = "SAT";
                    break;
            }
            if (i == weeks.length - 1) {
                stringBuilder.append(weeks[i]);
            } else {
                stringBuilder.append(weeks[i] + ",");
            }
        }
        return stringBuilder.toString();
    }

//    public void getHourAndMinute(String str){}
    public static List<String> getDayAndMonth(String str1, String str2) {
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String[] array1 = str1.split("/");
        String[] array2 = str2.split("/");

        String month1 = array1[1];
        String day1 = array1[2];

        String month2 = array2[1];
        String day2 = array2[2];
        if (month1.equals(month2)) {
            builder.append(day1 + "-" + day2);
            builder.append(" " + month1);
            list.add(builder.toString());
        } else {
            StringBuilder builder1 = new StringBuilder();
            int temp = CalendarUtil.dayByMonth(str1);
            builder.append(day1 + "-" + temp);
            builder.append(" " + month1);
            builder1.append("1-" + day2);
            builder1.append(" " + month2);
            list.add(builder.toString());
            list.add(builder1.toString());
        }
        return list;
    }

    public static String getCmd(int sceneId) {
        String command;
        switch (sceneId) {
            case 21:
                command = CommandDict.MESH_OFF.getCmd();
                break;
            case 22:
                command = CommandDict.MESH_ON.getCmd();
                break;
            default:
                StringBuilder cmd = new StringBuilder(CommandDict.SCENE.getCmd());
                String strHex = Integer.toHexString(sceneId).toUpperCase();
                if (strHex.length() == 1) {
                    cmd.append("0" + sceneId);
                } else {
                    cmd.append(sceneId);
                }
                cmd.append("CCCC");
                command = cmd.toString();
                break;
        }
        return command;
    }

    public static void main(String[] args) {
        String str1 = "2020/2/26";
        String str2 = "2020/3/5";
        List<String> list=getDayAndMonth(str1,str2);
        for (String str:list){
            System.out.println(str);
        }
    }
}
