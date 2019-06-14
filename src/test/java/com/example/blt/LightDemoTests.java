package com.example.blt;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.LightListDao;
import com.example.blt.entity.LightDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class LightDemoTests {

    @Resource
    private LightListDao lightListDao;

    @Test
    public void test() {

        List<Map<String, Object>> centerLNumList = lightListDao.getCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getPlaceLNum();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lightState", "");//所有灯的状态
        jsonObject.put("placeLNumList", placeLNumList);//每个区域的灯个数
        jsonObject.put("centerLNumList", centerLNumList);//每个楼层灯总个数
        jsonObject.put("placeLState", "");//每个区域灯的总开关状态
        jsonObject.put("centerLState", "");//每个楼层灯的总开关状态
        System.out.println(jsonObject.toJSONString());
    }

}
