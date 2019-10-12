package com.example.blt;

import com.example.blt.dao.*;
import com.example.blt.entity.LightDemo;
import com.example.blt.service.ControlCenterService;
import com.example.blt.service.NewMonitorService;
import com.example.blt.service.TpadOfficeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Resource
    private ControlCenterService controlCenterService;

    @Resource
    private TpadOfficeService tpadOfficeService;

    @Resource
    private TpadOfficeDao tpadOfficeDao;

    @Test
    public void test() {

//        CommandLight officeLightInfo = lightListDao.getCommandInfo();
//        System.out.println(officeLightInfo.toString());
    }

    @Test
    public void test2() {
//        List<LightDemo> exhibitionFromPhoneByGroup = lightListDao.getExhibitionFromPhoneByGroup(1, "0");
//        exhibitionFromPhoneByGroup.stream().forEach(System.out::println);


        List<Object> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<Object> list2 = new ArrayList<>();
        Map<String, Object> map2 = new HashMap<>();
        List<Object> list3 = new ArrayList<>();
        Map<String, Object> map3 = new HashMap<>();
        map3.put("group", 1);
        list3.add(map3);
        map2.put("place", list3);
        map2.put("id", 1);
        list2.add(map2);
        map.put("mname", list2);
        map.put("id", 1);
        list.add(map);
        System.out.println(list.toString());
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
    public void test5() {
//        List<LightDemo> lightState = monitor4Dao.getIntelligenceLightInfo();
//        List<CenterException> ms = webCmdDao.getMnames();
//        Map<String,Object> indexFloorStatus = newMonitorService.getIndexFloorStatus(lightState);
//        System.out.println(indexFloorStatus.toString());
        List<String> list;
        System.out.println(111);
    }

    @Test
    public void test6() {
//        List<LightDemo> floorLightStatus = newMonitorDao.getFloorLightStatus("1楼");
//        System.out.println(floorLightStatus.toString());
//        List<LightDemo> lightState = newMonitorService.getFloorLights("1楼");
//        Map<String,Object> floorLights = newMonitorService.getFloorLightsStatus(lightState,"1楼");
//        System.out.println(floorLights.toString());
    }

    static int getValue(int[] values, int length) {
        if (length <= 0)
            return 0;
        int tmpMax = -1;
        for (int i = 0; i < length; i++) {
            int value = getValue(values, length - i - 1);
            tmpMax = Math.max(tmpMax, values[i] + value);
        }
        return tmpMax;
    }

    @Test
    public void test7() {
        int[] values = new int[]{3, 7, 1, 3, 9};
        int rodLength = values.length;
        System.out.println("Max rod value: " + getValue(values, rodLength));
    }

    @Test
    public void getGroups() {
        String gname = null;
//        List<ControlMaster> controlGroups = controlCenterService.getControlGroups(gname, null);
//        System.out.println(controlGroups.toString());
    }

    @Test
    public void test8() {
        char[] chars = "020C0C070103060F".toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 != 0) {
                buffer.append(chars[i]);
            }
        }
        System.out.println(buffer.toString());
    }

    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    @Test
    public void test9() {
        String s = "020C0C070103060F";
        System.out.println(toStringHex(s));
    }

    @Test
    public void test10() {
        String s = "CA";
        System.out.println(Integer.parseInt(s, 16));
    }

    @Test
    public void test11() {
        Map<Integer, String> colors = new HashMap<>();
        Map<Integer, String> luminances = new HashMap<>();
        int colorsCount = 0;
        int luminancesCount = 19;
        for (int i = 0; i <= 100; i = i + 5) {
            String str = Integer.toHexString(colorsCount).toUpperCase();
            if (str.length()==1){
                str = "0"+str;
            }
            colors.put(i,str);
            colorsCount = colorsCount + 1;
        }
        for (int i = 0; i <= 100; i = i + 5) {
            String str = Integer.toHexString(luminancesCount).toUpperCase();
            if (str.length()==1){
                str = "0"+str;
            }
            if (i == 100){
                str = "00";
            }
            luminances.put(i,str);
            luminancesCount = luminancesCount - 1;
        }
        System.out.println(colors);
        System.out.println(luminances);
    }

    @Test
    public void test12(){
        String otaVersion = "0110";
        String preOtaVersion;
        String LastOtaVersion;
                preOtaVersion = otaVersion.substring(0,2);
                LastOtaVersion = otaVersion.substring(2,4);
        System.out.println(preOtaVersion+" ："+LastOtaVersion);
    }

    @Test
    public void test13(){
        Map<String,Object> groupAndScenseMap = new ConcurrentHashMap<>();
        int groupNum =  20;
        int sceneNum = 20;
        String numName = "groupNum";
        String listName = "groups";
        Map<String,Object> groupMap = setParameter(numName,listName,groupNum);
        numName = "sceneNum";
        listName = "scenes";
        Map<String,Object> sceneMap = setParameter(numName,listName,sceneNum);
        groupAndScenseMap.putAll(groupMap);
        groupAndScenseMap.putAll(sceneMap);
        System.out.println(groupAndScenseMap.toString());
    }

    public  static Map<String,Object> setParameter(String numName,String listName,int num){
        Map<String,Object> map = new ConcurrentHashMap<>();
        List<String> list = new ArrayList<>();
        for (int i=0;i<num;i++){
            String hexStr = String.format("%02x",i).toUpperCase();
            list.add(hexStr);
        }
        map.put(numName,num);
        map.put(listName,list);
        return map;
    }

    @Test
    public void test14(){
        Integer eGid = tpadOfficeDao.getEGid(1, 1);
        System.out.println(eGid);
    }
}
