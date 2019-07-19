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

        Map<String,List> map1 = new HashMap<>();
        List<Map> list1 = new ArrayList<>();
        Map<String,List> map2 = new HashMap<>();
        List<Map> list2 = new ArrayList<>();
        Map map3 = new HashMap();
        map3.put("Place","区域1");
        map3.put("num",10);
        map3.put("state",0);
        list2.add(map3);
        map3 = new HashMap();
        map3.put("Place","区域2");
        map3.put("num",15);
        map3.put("state",1);
        list2.add(map3);
        map2.put("Place",list2);
        list1.add(map2);
        map1.put("total",list1);
        System.out.println(map1.toString());
//        System.out.println(list1.toString());


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
        List<LightDemo> lightState = monitor4Dao.getIntelligenceLightInfo();
//        List<CenterException> ms = webCmdDao.getMnames();
        Map<String,Object> indexFloorStatus = newMonitorService.getIndexFloorStatus(lightState);
        System.out.println(indexFloorStatus.toString());
    }

}
