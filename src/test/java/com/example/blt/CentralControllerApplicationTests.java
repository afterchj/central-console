package com.example.blt;

import com.example.blt.dao.ConsoleDao;
import com.example.blt.entity.HostInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class CentralControllerApplicationTests {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConsoleDao consoleDao;

    @Test
    public void contextLoads() {
        HostInfo host = new HostInfo();
        host.setStatus(true);
        host.setIp("192.168.56.1");
        host.setCreate_date(new Date());
        host.setLog_date(new Date());
        HostInfo result = consoleDao.saveAndFlush(host);
        HostInfo s = consoleDao.getByIp("192.168.56.1");
        logger.warn("id=" + result.getId());
//        consoleDao.delete(s);
    }
}
