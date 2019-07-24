package com.example.blt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.service.CommandService;
import com.example.blt.utils.ConsoleUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class CentralControllerApplicationTests {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    CommandService commandService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private HostService hostService;
//    @Autowired
//    private ConsoleService consoleService;
//
//    @Autowired
//    private LightService lightService;

    @Test
    public void testRedis() {
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        ValueOperations<String, List> operationsList = redisTemplate.opsForValue();
        Set<Map> lmacSet = operations.get(ConsoleKeys.lMAC.getValue());
        Set<Map> vaddrSet = operations.get(ConsoleKeys.VADDR.getValue());
        List<Map> vaddr = sqlSessionTemplate.selectList("console.getVaddr");
        String key1 = ConsoleKeys.VADDR.getValue();
        String key2 = ConsoleKeys.lMAC.getValue();
        operationsList.set("test_vaddr", vaddr, 1, TimeUnit.MINUTES);
        List set1 = operationsList.get("test_vaddr");
        logger.warn("lmacSet=" + set1);
        logger.warn("vaddrSet=" + vaddrSet);
    }

    //    @Test
//    public void contextLoads() {
////        hostService.updateByIp("192.168.56.1", false);
//        HostInfo s = hostService.getByIp("127.0.0.1");
//        logger.warn("id=" + s.getId());
////        consoleDao.delete(s);
//    }
//
//    @Test
//    public void testGetBean() {
//        ConsoleInfo info = new ConsoleInfo();
//        info.setIp("127.0.0.1");
//        info.setCmd("010101010101");
//        info.setLog_date(new Date());
//        consoleService.save(info);
//        logger.warn("id=" + consoleService.getByIp("192.168.56.1").getId());
//    }
//
//    @Test
//    public void testJoin() {
//        HostInfo hostInfo = new HostInfo();
//        hostInfo.setId(6);
////        hostInfo.setIp("192.168.51.1");
//        LightInfo lightInfo = new LightInfo();
//        lightInfo.setLmac("f0:ac:d7:6d:44:e7");
//        HostInfo s = hostService.getByIp("127.0.0.1");
////        lightInfo.setHostInfo(hostInfo);
//        logger.warn("id=" + s.getId());
//        lightService.save(lightInfo);
//
//    }
//
//    @Test
//    public void testList() {
//        HostInfo s = hostService.getByIp("192.168.51.95");
//        List<LightInfo> list = lightService.getByHostInfo(s);
//        logger.warn("size=" + list.size() + ",id=" + list.get(0));
//        logger.warn("id=" + list.get(0));
//    }

    @Test
    public void testLog() {
        System.out.println("This is println message.");
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录warn级别的信息
        logger.warn("This is warn message.");
        // 记录error级别的信息
        logger.error("This is error message.");

        String deviceIds = "{\"DBD47F5FD33A464ABA32AA97D00EB1CE\",\"DBD47F5FD33A464ABA32AA97D00EB1CE\",\"B59B48A74ACB4EB8A2C181AEDFBF42A6\"}";
        try {
            JSONObject jsonObject = JSONObject.parseObject(deviceIds);
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        String jsonString = JSON.toJSONString(deviceIds);
        System.out.println(jsonString.contains("B59B48A74ACB4EB8A2C181AEDFBF42A6"));
    }

    @Test
    public void testJpa() {
        System.out.println("commandDao" + commandService);
    }

    @Test
    public void testRedisIncrement() {
        ConsoleUtil.saveInfo(ConsoleKeys.LTIMES.getValue(), 1);
        for (int i = 0; i < 3; i++) {
            redisTemplate.opsForValue().increment(ConsoleKeys.LTIMES.getValue());
        }
        Integer result = (Integer) redisTemplate.opsForValue().get(ConsoleKeys.LTIMES.getValue());
        logger.warn("result [{}]", result);
    }
}
