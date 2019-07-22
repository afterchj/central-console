package com.example.blt.service;

import com.example.blt.dao.NewMonitorDao;
import com.example.blt.dao.WebCmdDao;
import com.example.blt.entity.CenterException;
import com.example.blt.entity.LightDemo;
import com.example.blt.entity.Mnames;
import com.example.blt.entity.Place;
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

    public Map<String,Object> getIndexFloorStatus( List<LightDemo> lightState) {
        List<CenterException> ms = webCmdDao.getMnames();
        List<Mnames> mnames = newMonitorDao.getMnames();
        List<CenterException> floorsExceptions = newMonitorDao.getIndexFloorException();//每个楼层故障灯的个数
        List<Place> placesExceptions = newMonitorDao.getPlacesExceptions();//每个区域故障灯个数
        List<Place> places = newMonitorDao.getPlaces();
        Map<String,Object> map = new HashMap<>();

        //ms:mname,Place,groupId 每个组的开关状态
        for (int i = 0; i < ms.size(); i++) {
            String msMname = ms.get(i).getMname();
            int msPlace = ms.get(i).getPlace();
            int msGroupId = ms.get(i).getGroupId();
            for (int j = 0; j < lightState.size(); j++) {
                String mname = lightState.get(j).getMname();
                int place = lightState.get(j).getPlace();
                int groupId = lightState.get(j).getGroupId();
                String status = lightState.get(j).getStatus();
                if (msMname.equals(mname) && msPlace == place ) {
                    if (msGroupId == groupId){
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

        for (int i=0;i<places.size();i++){
            if (placesExceptions.size()>0){
                for (int j=0;j<placesExceptions.size();j++){
                    if (places.get(i).getMname().equals(placesExceptions.get(j).getMname())&&places.get(i).getPlace()
                            ==placesExceptions.get(j).getPlace()){
                        places.get(i).setException(placesExceptions.get(j).getException());
                    }
                }
            }
            for (int c = 0; c < ms.size(); c++) {
                if (places.get(i).getMname().equals(ms.get(c).getMname())&&places.get(i).getPlace()==ms.get(c).getPlace()){
                    if (ms.get(i).getOff()==1 && ms.get(i).getOn()==1){
                        places.get(i).setDiff(1);
                    }
                }
            }
        }
//        System.out.println("places: "+places.toString());
        //楼层异常个数
        List<Map<String,Object>> placeList;
        for (int i=0;i<mnames.size();i++){
            placeList = new ArrayList<>();
            if (floorsExceptions.size()>0){
                for (int j=0;j<floorsExceptions.size();j++){
                    if (mnames.get(i).getMname().equals(floorsExceptions.get(j).getMname())){
                        mnames.get(i).setException(floorsExceptions.get(j).getException());
                    }
                }
            }

            Map<String,Object> placeMap;
            for (int c=0;c<places.size();c++){
                if (mnames.get(i).getMname().equals(places.get(c).getMname())){
                    placeMap = new HashMap<>();
                    placeMap.put("place",places.get(c).getPlace());
                    placeMap.put("exception",places.get(c).getException());
                    placeMap.put("diff",places.get(c).getDiff());
                    placeList.add(placeMap);
                }
            }
            mnames.get(i).setPlaceList(placeList);
        }
//        System.out.println(mnames.toString());
        //统计每个楼层组的开关状态
        for (int i = 0; i < ms.size(); i++) {
            String msMname = ms.get(i).getMname();
            for (int j = 0; j < mnames.size(); j++) {
                String mname = mnames.get(j).getMname();
                if (msMname.equals(mname)) {
                    if (ms.get(i).getOn()==1 && ms.get(i).getOff()==1){
                        //组内既有开又有关 该组为开的状态
                        mnames.get(j).setOn(1);
                    }
                    if (ms.get(i).getOn()==0 && ms.get(i).getOff()==0){
                        //组内没有开也没有管 异常状态 该组状态为关
                        mnames.get(j).setOff(1);
                    }
                    if (ms.get(i).getOn()==1 && ms.get(i).getOff()==0){
                        //组内只有开 该组为开的状态
                        mnames.get(j).setOn(1);
                    }
                    if (ms.get(i).getOn()==0 && ms.get(i).getOff()==1){
                        //组内只有关 该组为关的状态
                        mnames.get(j).setOff(1);
                    }
                }
            }
        }
        int totalStatus=1;//总楼层默认状态为关
        for (int j = 0; j < mnames.size(); j++) {
            int on = mnames.get(j).getOn();
//            int off = mnames.get(j).getOff();
            if (on==1 ){
                totalStatus=0;//楼层内的组只要有开即为开
                mnames.get(j).setOn(1);//1 存在开 0 存在关
                mnames.get(j).setOff(0);
            }
            if (on==0){
                //楼层内的组状态没有开 楼层状态为关
                mnames.get(j).setOn(0);
                mnames.get(j).setOff(0);
            }
//            if (on==1 && off==0){
//                totalStatus=0;//楼层内的组状态只有开 楼层状态为开
//                mnames.get(j).setOn(1);
//                mnames.get(j).setOff(0);
//            }
//            if (on==0 && off==1){
//                //楼层内的组状态只有开 楼层状态为开
//                mnames.get(j).setOn(0);
//                mnames.get(j).setOff(0);
//            }
        }
        map.put("status",totalStatus);
        map.put("floor",mnames);
//        System.out.println(map.toString());
        return map;
    }
}
