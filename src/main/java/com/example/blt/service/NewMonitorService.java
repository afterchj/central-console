package com.example.blt.service;

import com.example.blt.dao.NewMonitorDao;
import com.example.blt.entity.CenterException;
import com.example.blt.entity.LightDemo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public List<CenterException> getIndexFloorStatus(List<CenterException> mnames, List<LightDemo> lightState,
                                                     List<CenterException> ms) {
        List<LightDemo> lightDemos = newMonitorDao.getIndexFloorException();
        for (int i = 0; i < lightDemos.size(); i++) {
            String lightDemosMname = lightDemos.get(i).getMname();
            for (int j = 0; j < mnames.size(); j++) {
                String mname = mnames.get(j).getMname();
                if (lightDemosMname.equals(mname)) {
                    mnames.get(j).setException(mnames.get(j).getException() + 1);
                }
            }
        }
        //ms:mname,place,groupId
        for (int i = 0; i < ms.size(); i++) {
            String msMname = ms.get(i).getMname();
            int msPlace = ms.get(i).getPlace();
            int msGroupId = ms.get(i).getGroupId();
            for (int j = 0; j < lightState.size(); j++) {
                String mname = lightState.get(j).getMname();
                int place = lightState.get(j).getPlace();
                int groupId = lightState.get(j).getGroupId();
                String status = lightState.get(j).getStatus();

                if (msMname.equals(mname) && msPlace == place && msGroupId == groupId) {
                    if (status != null) {
                        if ("0".equals(status)) {
                            ms.get(i).setOn(1);
                        } else {
                            ms.get(i).setOff(1);
                        }
                        if (ms.get(j).getOn() == 1 && ms.get(j).getOff() == 1) {
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < ms.size(); i++) {
            String msMname = ms.get(i).getMname();
            for (int j = 0; j < mnames.size(); j++) {
                String mname = mnames.get(j).getMname();
                if (msMname.equals(mname)) {

                }
            }
        }


        return mnames;
    }
}
