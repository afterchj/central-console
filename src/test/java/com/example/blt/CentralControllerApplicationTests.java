package com.example.blt;

import com.example.blt.entity.ConsoleInfo;
import com.example.blt.entity.HostInfo;
import com.example.blt.entity.LightInfo;
import com.example.blt.service.ConsoleService;
import com.example.blt.service.HostService;
import com.example.blt.service.LightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class CentralControllerApplicationTests {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HostService hostService;
    @Autowired
    private ConsoleService consoleService;

    @Autowired
    private LightService lightService;

    @Test
    public void contextLoads() {
//        hostService.updateByIp("192.168.56.1", false);
        HostInfo s = hostService.getByIp("127.0.0.1");
        logger.warn("id=" + s.getId());
//        consoleDao.delete(s);
    }

    @Test
    public void testGetBean() {
        ConsoleInfo info = new ConsoleInfo();
        info.setIp("127.0.0.1");
        info.setCmd("010101010101");
        info.setLog_date(new Date());
        consoleService.save(info);
        logger.warn("id=" + consoleService.getByIp("192.168.56.1").getId());
    }

    @Test
    public void testJoin() {
        HostInfo hostInfo = new HostInfo();
        hostInfo.setId(6);
//        hostInfo.setIp("192.168.51.1");
        LightInfo lightInfo = new LightInfo();
        lightInfo.setHostInfo(hostInfo);
        lightInfo.setLmac("f0:ac:d7:6d:44:e7");
        HostInfo s = hostService.getByIp("127.0.0.1");
//        lightInfo.setHostInfo(hostInfo);
        logger.warn("id=" + s.getId());
        lightService.save(lightInfo);

    }

    @Test
    public void testList() {
        HostInfo s = hostService.getByIp("192.168.51.95");
        List<LightInfo> list = lightService.getByHostInfo(s);
        logger.info("size=" + list.size() + ",id=" + list.get(0).getHostInfo().getId());
        logger.info("id=" + list.get(0).getHostInfo().getId());
    }
}
