package com.example.blt;

import com.example.blt.dao.LightListDao;
import com.example.blt.entity.CommandLight;
import com.example.blt.entity.LightDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class LightDemoTests {

    @Resource
    private LightListDao lightListDao;

    @Test
    public void test() {

        CommandLight officeLightInfo = lightListDao.getCommandInfo();
        System.out.println(officeLightInfo.toString());
    }

    @Test
    public void test2(){
        List<LightDemo> exhibitionFromPhoneByGroup = lightListDao.getExhibitionFromPhoneByGroup(1, "0");
        exhibitionFromPhoneByGroup.stream().forEach(System.out::println);
    }

}
