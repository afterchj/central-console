package com.example.blt;

import com.example.blt.dao.LightListDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class LightDemoTests {

    @Resource
    private LightListDao lightListDao;

    @Test
    public void test() {

//        CommandLight officeLightInfo = lightListDao.getCommandInfo();
//        System.out.println(officeLightInfo.toString());
    }

    @Test
    public void test2(){
//        List<LightDemo> exhibitionFromPhoneByGroup = lightListDao.getExhibitionFromPhoneByGroup(1, "0");
//        exhibitionFromPhoneByGroup.stream().forEach(System.out::println);
    }

    @Test
    public void test3(){
        String command = "7701041609003766";
        String group = command.substring(9,10);
        String state = command.substring(12,14);
        String type = command.substring(6,8);
        System.out.println(group+" : "+state+" : "+command.length()+" : "+type);
        command="77010315373766";
        group = command.substring(6,8);
        state = command.substring(10,12);
        type = command.substring(6,8);
        System.out.println(group+" : "+state+" : "+command.length()+" : "+type);
    }

    @Test
    public void test4(){
        String str = "0A";
        System.out.println(str);
//        System.out.println(Integer.parseInt(str,16));
    }

}
