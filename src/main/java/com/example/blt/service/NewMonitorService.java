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

        for (int j = 0; j < mnames.size(); j++) {
            String mname = mnames.get(j).getMname();
            for (int i = 0; i < lightState.size(); i++) {
                String lightStateMname = lightState.get(i).getMname();
                String lightStateStatus = lightState.get(i).getStatus();
                if (lightStateMname.equals(mname)) {
                    if ("0".equals(lightState.get(i).getStatus())) {
                        mnames.get(j).setOn(1);
                    } else if ("1".equals(lightStateStatus)) {
                        mnames.get(j).setOff(1);
                    }
                    if (mnames.get(j).getOn() == 1 && mnames.get(j).getOff() == 1) {
                        break;
                    }

                }
            }
        }

        return mnames;
    }
}
