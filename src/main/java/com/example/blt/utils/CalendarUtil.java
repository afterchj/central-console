package com.example.blt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hongjian.chen
 * @date 2020/2/24 11:19
 */
public class CalendarUtil {

    public static String getNextDate(String dateStr, int offset) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return dateStr;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return sdf.format(calendar.getTime());
    }

    public static int dayByMonth(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        Date date;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            //对于2月份需要判断是否为闰年
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }

            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(getNextDate("2020/2/25", 10) + ":" + dayByMonth("2020/3/24"));
    }
}
