package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.ControlCenterDao;
import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.dd.Week;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.StringJoiner;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-03 14:14
 **/
@Service
public class ControlCenterService {

    @Resource
    private ControlCenterDao controlCenterDao;

    public List<TimeLine> getTimeLinesByMeshId(String meshId) {
        List<TimeLine> timeLines = controlCenterDao.getTimeLinesByMeshId(meshId);
        for (TimeLine timeLine:timeLines){
            String dayObj = timeLine.getDayObj();
            JSONObject jsonDayObj = JSONObject.parseObject(dayObj);
            String repetition = timeLine.getRepetition();
            String itemSet = timeLine.getItemSet();
            timeLine.setWeek(getWeeks(jsonDayObj,repetition,itemSet));
        }
        return timeLines;
    }

    public String getWeeks(JSONObject jsonWeek,String repetition,String itemSet){
        jsonWeek.getInteger(Week.MON.getWeekCN());
        StringJoiner sj = new StringJoiner(",");
        sj.add(itemSet).add(repetition);
        if (jsonWeek.getInteger("mon").equals(1)){
            sj.add(Week.MON.getWeekCN());
        }
        if (jsonWeek.getInteger("tus").equals(1)){
            sj.add(Week.TUS.getWeekCN());
        }
        if (jsonWeek.getInteger("wed").equals(1)){
            sj.add(Week.WED.getWeekCN());
        }
        if (jsonWeek.getInteger("thr").equals(1)){
            sj.add(Week.THR.getWeekCN());
        }
        if (jsonWeek.getInteger("fri").equals(1)){
            sj.add(Week.FRR.getWeekCN());
        }
        if (jsonWeek.getInteger("sat").equals(1)){
            sj.add(Week.SAT.getWeekCN());
        }
        if (jsonWeek.getInteger("sun").equals(1)){
            sj.add(Week.SUN.getWeekCN());
        }
        return sj.toString();
    }

    public  List<TimePoint> getTimePointByTsid(Integer tsid) {
        return controlCenterDao.getTimePointByTsid(tsid);
    }
}
