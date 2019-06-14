package com.example.blt;

import com.example.blt.dao.HostDao;
import com.example.blt.entity.ConsoleInfo;
import com.example.blt.entity.HostInfo;
import com.example.blt.service.ConsoleService;
import com.example.blt.service.HostService;
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
    @Test
    public void contextLoads() {
        hostService.updateByIp("192.168.56.1",false);
        HostInfo s = hostService.getByIp("192.168.56.1");
        logger.warn("id=" + s.getId()+",status="+s.getStatus());
//        consoleDao.delete(s);
    }

    @Test
    public void testGetBean(){
        ConsoleInfo info=new ConsoleInfo();
        info.setIp("127.0.0.1");
        info.setCmd("010101010101");
        info.setLog_date(new Date());
        consoleService.save(info);
        logger.warn("id=" +consoleService.getByIp("192.168.56.1").getId());
    }

    @Test
    public void testList(){
        List<ConsoleInfo> list=consoleService.getAll();
        logger.info("ip=",list.size());
    }
}
