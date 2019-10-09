package com.example.blt.service;

import com.example.blt.dao.TpadOfficeDao;
import com.example.blt.entity.office.TypeOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 11:17
 **/
@Service
public class TpadOfficeService {

    @Resource
    private TpadOfficeDao tpadOfficeDao;

    public List<String> getHostId(String projectName) {
        List<Map<String,Object>> meshs = tpadOfficeDao.getMesh(projectName);
        List<String> hostIds = new ArrayList<>();
        String meshId;
        String hostId;
        List<Map<String,Object>> hosts;
        if (meshs.size()>0){
            for (Map<String,Object> mesh:meshs){
                hostId = "";
                meshId = (String) mesh.get("meshId");
                hosts = tpadOfficeDao.getHost(meshId);
                if (hosts.size()>1){
                    for (Map<String,Object> host:hosts ){
                        if ((Boolean) host.get("master")){//主控poe
                            hostId = (String) host.get("hostId");
                        }
                    }
                }
                if (StringUtils.isBlank(hostId) && hosts.size()>0){//只有一个poe or 没有主控poe
                    hostId = (String) hosts.get(0).get("hostId");
                }
                hostIds.add(hostId);
            }
        }

        return hostIds;
    }

    public List<Map<String,Object>> getParameterSetting(String projectName) {
        List<Map<String,Object>> list = tpadOfficeDao.getParameterSetting();
        List<Map<String,Object>> meshs = tpadOfficeDao.getMesh(projectName);
        list.addAll(meshs);
        return list;
    }

    public List<Map<String, Object>> getUnits(String unit) {
        TypeOperation type = TypeOperation.getType(unit);
        List<Map<String,Object>> units = new ArrayList<>();
        switch (type){
            case GROUP:
                break;
            case Place:
                units = tpadOfficeDao.getPlaces();
                break;
            case MESH:
                units = tpadOfficeDao.getMeshs();
                break;
        }
        return units;
    }

    public String getUnitName(String project) {
        return tpadOfficeDao.getUnitName(project);
    }
}
