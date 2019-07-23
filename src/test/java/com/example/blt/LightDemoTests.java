package com.example.blt;

import com.example.blt.dao.LightListDao;
import com.example.blt.dao.Monitor4Dao;
import com.example.blt.dao.NewMonitorDao;
import com.example.blt.dao.WebCmdDao;
import com.example.blt.entity.LightDemo;
import com.example.blt.service.NewMonitorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class LightDemoTests {

    @Resource
    private LightListDao lightListDao;

    @Resource
    private NewMonitorService newMonitorService;

    @Resource
    private WebCmdDao webCmdDao;

    @Resource
    private NewMonitorDao newMonitorDao;

    @Resource
    private Monitor4Dao monitor4Dao;

    @Test
    public void test() {

//        CommandLight officeLightInfo = lightListDao.getCommandInfo();
//        System.out.println(officeLightInfo.toString());
    }

    @Test
    public void test2() {
//        List<LightDemo> exhibitionFromPhoneByGroup = lightListDao.getExhibitionFromPhoneByGroup(1, "0");
//        exhibitionFromPhoneByGroup.stream().forEach(System.out::println);

        Map<String,Object> map1 = new HashMap<>();
        String test = null;
        map1.put("test",test);
        System.out.println(map1.toString());


    }

    @Test
    public void test3() {
        List<LightDemo> lightDemoList = new ArrayList<>();
        System.out.println(lightDemoList.size());
    }

    @Test
    public void test4() {
        String str = "0A";
        System.out.println(str);
//        System.out.println(Integer.parseInt(str,16));
    }

    @Test
    public void test5(){
//        List<LightDemo> lightState = monitor4Dao.getIntelligenceLightInfo();
//        List<CenterException> ms = webCmdDao.getMnames();
//        Map<String,Object> indexFloorStatus = newMonitorService.getIndexFloorStatus(lightState);
//        System.out.println(indexFloorStatus.toString());
        List<String> list;
        System.out.println(111);
    }

    @Test
    public void test6(){
//        List<LightDemo> floorLightStatus = newMonitorDao.getFloorLightStatus("1楼");
//        System.out.println(floorLightStatus.toString());
//        List<LightDemo> lightState = newMonitorService.getFloorLights("1楼");
//        Map<String,Object> floorLights = newMonitorService.getFloorLightsStatus(lightState,"1楼");
//        System.out.println(floorLights.toString());
    }

}
