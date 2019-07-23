package com.example.blt.service;

import com.example.blt.dao.NewMonitorDao;
import com.example.blt.dao.WebCmdDao;
import com.example.blt.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-18 14:25
 **/
@Service
public class NewMonitorService {

    @Resource
    private NewMonitorDao newMonitorDao;

    @Resource
    private WebCmdDao webCmdDao;

    public Map<String, Object> getIndexFloorStatus(List<LightDemo> lightState) {
        List<CenterException> ms = webCmdDao.getMnames();
        List<Mnames> mnames = newMonitorDao.getMnames();
        List<CenterException> floorsExceptions = newMonitorDao.getIndexFloorException();//每个楼层故障灯的个数
        List<Place> placesExceptions = newMonitorDao.getPlacesExceptions();//每个区域故障灯个数
        List<Place> places = newMonitorDao.getPlaces();
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < ms.size(); i++) {
            String msMname = ms.get(i).getMname();
            int msPlace = ms.get(i).getPlace();
            int msGroupId = ms.get(i).getGroupId();
            for (int j = 0; j < lightState.size(); j++) {
                String mname = lightState.get(j).getMname();
                int place = lightState.get(j).getPlace();
                int groupId = lightState.get(j).getGroupId();
                String status = lightState.get(j).getStatus();
                if (msMname.equals(mname) && msPlace == place) {
                    if (msGroupId == groupId) {
                        if (status != null) {
                            if ("0".equals(status)) {
                                ms.get(i).setOn(1);
                            } else {
                                ms.get(i).setOff(1);
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(ms.toString());

        for (int i = 0; i < places.size(); i++) {
            int placePlace = places.get(i).getPlace();
            String placeMname = places.get(i).getMname();
            if (placesExceptions.size() > 0) {
                for (int j = 0; j < placesExceptions.size(); j++) {
                    if (places.get(i).getMname().equals(placesExceptions.get(j).getMname()) && places.get(i).getPlace()
                            == placesExceptions.get(j).getPlace()) {
                        places.get(i).setException(placesExceptions.get(j).getException());
                        break;
                    }
                }
            }
            for (int c = 0; c < ms.size(); c++) {
                int place = ms.get(c).getPlace();
                String mname = ms.get(c).getMname();
                int on = ms.get(c).getOn();
                int off = ms.get(c).getOff();
                if (mname.equals(placeMname) && place == placePlace) {
                    if (on == 1 && off == 1) {
                        places.get(i).setDiff(1);
                        break;
                    }
                }
            }
        }
//        System.out.println("places: "+places.toString());
        //楼层异常个数
        List<Map<String, Object>> placeList;
        for (int i = 0; i < mnames.size(); i++) {
            placeList = new ArrayList<>();
            if (floorsExceptions.size() > 0) {
                for (int j = 0; j < floorsExceptions.size(); j++) {
                    if (mnames.get(i).getMname().equals(floorsExceptions.get(j).getMname())) {
                        mnames.get(i).setException(floorsExceptions.get(j).getException());
                        break;
                    }
                }
            }

            Map<String, Object> placeMap;
            for (int c = 0; c < places.size(); c++) {
                if (mnames.get(i).getMname().equals(places.get(c).getMname())) {
                    placeMap = new HashMap<>();
                    placeMap.put("place", places.get(c).getPlace());
                    placeMap.put("exception", places.get(c).getException());
                    placeMap.put("diff", places.get(c).getDiff());
                    placeList.add(placeMap);
                }
            }
            mnames.get(i).setPlaceList(placeList);
        }
//        System.out.println(mnames.toString());
        //统计每个楼层组的开关状态
        int totalStatus = 1;//总楼层默认状态为关
        for (int i = 0; i < ms.size(); i++) {
            String msMname = ms.get(i).getMname();
            for (int j = 0; j < mnames.size(); j++) {
                String mname = mnames.get(j).getMname();
                if (msMname.equals(mname)) {
//                    if (ms.get(i).getOn() == 1 && ms.get(i).getOff() == 1) {
//                        //组内既有开又有关 该组为开的状态
//                        mnames.get(j).setOn(1);
//                    }
//                    if (ms.get(i).getOn() == 0 && ms.get(i).getOff() == 0) {
//                        //组内没有开也没有管 异常状态 该组状态为关
//                        mnames.get(j).setOff(1);
//                    }
//                    if (ms.get(i).getOn() == 1 && ms.get(i).getOff() == 0) {
//                        //组内只有开 该组为开的状态
//                        mnames.get(j).setOn(1);
//                    }
//                    if (ms.get(i).getOn() == 0 && ms.get(i).getOff() == 1) {
//                        //组内只有关 该组为关的状态
//                        mnames.get(j).setOff(1);
//                    }
                    if (ms.get(i).getOn() == 1){
                        //组内既有开又有关 该组为开的状态
                        totalStatus = 0;
                        mnames.get(j).setOn(1);
                    }
                }
            }
        }

//        for (int j = 0; j < mnames.size(); j++) {
//            int on = mnames.get(j).getOn();
////            int off = mnames.get(j).getOff();
//            if (on == 1) {
//                totalStatus = 0;//楼层内的组只要有开即为开
//                mnames.get(j).setOn(1);//1 存在开 0 存在关
//                mnames.get(j).setOff(0);
//            }
//            if (on == 0) {
//                //楼层内的组状态没有开 楼层状态为关
//                mnames.get(j).setOn(0);
//                mnames.get(j).setOff(0);
//            }
//
////            if (on==1 && off==0){
////                totalStatus=0;//楼层内的组状态只有开 楼层状态为开
////                mnames.get(j).setOn(1);
////                mnames.get(j).setOff(0);
////            }
////            if (on==0 && off==1){
////                //楼层内的组状态只有开 楼层状态为开
////                mnames.get(j).setOn(0);
////                mnames.get(j).setOff(0);
////            }
//        }
        map.put("status", totalStatus);
        map.put("floor", mnames);
//        System.out.println(map.toString());
        return map;
    }

    public List<LightDemo> getFloorLights(String floor) {

        return newMonitorDao.getFloorLights(floor);
    }


    public Map<String, Object> getFloorLightsStatus(List<LightDemo> lightState, String floor) {
        Map<String, Object> map = new HashMap<>();
        List<PlaceWeb> places = newMonitorDao.getPlacesByFloor(floor);//单楼层内区域结构
        List<PlaceWeb> placesExceptions = newMonitorDao.getPlacesExceptionsByFloor(floor);//单楼层内每个区域故障灯个数
        List<CenterException> ms = webCmdDao.getMnamesByFloor(floor);//单楼层结构
        Integer centerLNum = newMonitorDao.getIntelligenceCenterLNumByFloor(floor);
        for (int i = 0; i < ms.size(); i++) {
            int msPlace = ms.get(i).getPlace();
            int msGroupId = ms.get(i).getGroupId();
            int count = 0;
            for (int j = 0; j < lightState.size(); j++) {
                int place = lightState.get(j).getPlace();
                int groupId = lightState.get(j).getGroupId();
                String status = lightState.get(j).getStatus();
                if (place == msPlace && groupId == msGroupId) {
                    if (status != null) {
                        if ("0".equals(status)) {
                            ms.get(i).setOn(1);
                        } else {
                            ms.get(i).setOff(1);
                        }
                    } else {
                        count = count + 1;
                        ms.get(i).setException(count);//一个组异常灯数
                    }
                }
            }
        }
//        System.out.println(ms.toString());
        List<GroupWeb> groupList;
        int totalStatus = 1;
        int exception = 0,diff = 0;
        //区域列表
        for (int i = 0; i < places.size(); i++) {
            groupList = new ArrayList<>();
            if (placesExceptions.size() > 0) {
                for (int j = 0; j < placesExceptions.size(); j++) {
                    if (places.get(i).getPlace() == placesExceptions.get(j).getPlace()) {
                        places.get(i).setException(placesExceptions.get(j).getException());//区域故障灯个数
                    }
                }
            }
            List<LightWeb> lightWebs;
            GroupWeb groupWeb;
            //组列表
            for (int j = 0; j < ms.size(); j++) {
                if (places.get(i).getPlace() == ms.get(j).getPlace()) {
                    groupWeb = new GroupWeb();
                    groupWeb.setGroupId(ms.get(j).getGroupId());
                    groupWeb.setStatus(1);
                    groupWeb.setException(ms.get(j).getException());
                    if (ms.get(j).getOn() == 1) {
                        groupWeb.setStatus(0);
                        places.get(i).setOn(1);//区域开关状态
                        totalStatus = 0;//只要有一个组是开的状态楼层的状态即为开
                    }
                    if (ms.get(j).getOn() == 1 && ms.get(j).getOff() == 1) {
                        groupWeb.setDiff(1);
                        diff = 1;
                    }
                    if (ms.get(j).getException()>0){
                        exception = 1;
                    }
                    LightWeb lightWeb;
                    lightWebs = new ArrayList<>();
                    //灯列表
                    for (int c = 0; c < lightState.size(); c++) {
                        if (ms.get(j).getGroupId()==lightState.get(c).getGroupId()){
                            lightWeb = new LightWeb();
                            lightWeb.setLname(lightState.get(c).getLname());
                            lightWeb.setStatus(lightState.get(c).getStatus());
                            lightWeb.setY(lightState.get(c).getY());
                            lightWebs.add(lightWeb);
                        }
                    }
                    groupWeb.setLightList(lightWebs);
                    groupList.add(groupWeb);
                }
            }
            places.get(i).setGroupList(groupList);
        }
//        System.out.println(places.toString());
        map.put("status",totalStatus);
        map.put("exception",exception);
        map.put("diff",diff);
        map.put("placeList",places);
        map.put("lightNum",centerLNum);
        return map;
    }


    public List<Map<String, Object>> getIntelligenceCenterLNum() {
        return newMonitorDao.getIntelligenceCenterLNum();
    }
}
