package com.example.blt;

import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.ConsoleUtil;
import com.example.blt.utils.SpringUtils;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by hongjian.chen on 2019/5/31.
 */
public class MainTest {

    private static Logger logger = LoggerFactory.getLogger(MainTest.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    public static void main(String[] args) {
//        ConsoleService consoleService = (ConsoleService) SpringJpaUtil.getBean(ConsoleService.class);
//        logger.warn("id=" + consoleService.getByIp("192.168.56.1").getId());
    }

    @Test
    public void testList() {
//        HostService hostService = (HostService) SpringJpaUtil.getBean(HostService.class);
//        logger.warn("size=" + hostService.getAll().size());
    }

//    @Test
//    public void testQuery1() {
//        EntityManager em = SpringJpaUtil.getEntityManager().createEntityManager();
//        try {
//            Query query = em.createQuery("from HostInfo as p where p.ip = ?1", HostInfo.class);
//            Query query = em.createQuery("from HostInfo", HostInfo.class);
//            query.setParameter(1, "127.0.0.1");
//            HostInfo info = (HostInfo) query.getSingleResult();
//            List<HostInfo> list=query.getResultList();
//            logger.warn("size=" + list.size());
//            System.out.println("id=" + info.getId());
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

    //    @Test
//    public void testQuery2() {
//        EntityManager em = SpringJpaUtil.getEntityManager().createEntityManager();
//        try {
//            Query query = em.createQuery("from HostInfo Group by ip", HostInfo.class);
//            List<HostInfo> info = query.getResultList();
//            System.out.println("id=" + info.size());
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
    @Test
    public void testStr() {
        Map map = new HashMap();
        map.put("host", "127.0.0.1");
        map.put("ctype", "C0");
        map.put("x", "32");
        map.put("y", "32");
        map.put("other", "77010315323266");
        sqlSessionTemplate.selectOne("console.saveConsole", map);
        System.out.println("result=" + map.get("result"));
        String str = "77040F0227E9010000713232000000000000CC";
        String str1 = "77040F0227";
        int index = str1.length();
        String vaddr = str.substring(index, index + 8);
        String x = str.substring(index + 10, index + 12);
        String y = str.substring(index + 12, index + 14);
        System.out.println("vaddr=" + vaddr + ",x=" + x + ",y=" + y);
    }

    @Test
    public void testSqlSession() {
        List hosts = sqlSessionTemplate.selectList("console.getHosts");
        Set set = new HashSet(hosts);
        Map map = new HashMap();
        map.put("ip", "127.0.0.1");
        map.put("status", true);
        sqlSessionTemplate.insert("console.insertHost", map);
//        String str = "77 04 0F 01 A9 10 64 D7 AC F0 7D 00 00 00 44 4F 03 0A CC CC ".replace(" ","");
//        String str = "77040F01A91064D7ACF07D000000444F030ACCCC";
//        String str = "77040F0227E9010000713232000000000000CC";
//        StrUtil.buildLightInfo(str,"127.0.0.1");
//        ExecuteTask.pingInfo("127.0.0.1", str);
        System.out.println(hosts + "\t" + set);
    }

    @Test
    public void testUpdate() {
//        List addr = sqlSessionTemplate.selectList("console.getVaddr");
//        Set list = new HashSet(addr);
        Set<String> set = new HashSet<>();
        set.add("1234");
        logger.warn("list=" + set);
//        Set<Map> set = new HashSet<>();
//        set.addAll(list);
        ConsoleUtil.saveVaddr(ConsoleKeys.VADDR.getValue(), set, 30);
        logger.warn("set=" + set.size());
        Map map = new HashMap();
        map.put("collection", set);
        map.put("host", "127.0.0.1");
        sqlSessionTemplate.update("console.saveUpdate2", map);
//        Set<Map> list = ConsoleUtil.persistHosts();
//        map.put("list", list);
//        sqlSessionTemplate.selectList("console.batchInsert", map);
//        logger.warn("list.size=" + list.size());
//        List<Map> list1 = sqlSessionTemplate.selectList("console.selectIn", map);
//        logger.warn("list1.size=" + list);
    }

    @Test
    public void testConsole() {
        List<Map> vaddr = sqlSessionTemplate.selectList("console.getVaddr");
//        System.out.println("light=" + ConsoleUtil.getLightSize("lmacn"));
        System.out.println("vaddr=" + vaddr.size());
        String key1 = ConsoleKeys.VADDR.getValue();
        String key2 = ConsoleKeys.lMAC.getValue();
        ConsoleUtil.saveInfo("test_vaddr", 101);
        System.out.println("size=" + ConsoleUtil.getValue("test_vaddr"));
        ConsoleUtil.cleanKey("test_vaddr");
        System.out.println("size=" + ConsoleUtil.getValue("test_vaddr"));
//        Set set2 = getInfo(key2);
    }

    @Test
    public void testRedis() {
        Set<Map> lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        Set<Map> vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
        logger.warn("lmacSet=" + lmacSet);
        logger.warn("vaddrSet=" + vaddrSet);
    }

    @Test
    public void testHash() throws InterruptedException {
        List list = sqlSessionTemplate.selectList("console.getLight", "192.168.10.12");
        Set set = new HashSet(list);
        ConsoleUtil.saveLight("t_light", "l_vaddr", "192.168.10.12", set);
        Map rs = ConsoleUtil.getLight("t_light");
        Integer size = sqlSessionTemplate.selectOne("console.selectIn", rs);
        logger.warn("size [{}]", size);
//        Set lmac = (Set) rs.get("l_vaddr");
        logger.warn("map [{}]", rs);
//        Thread.sleep(10000);
//        Map rs1 = ConsoleUtil.getLight("t_light");
//        logger.warn("ip [{}] \n lmac [{}]", rs1.get("ip"), rs1.get("lmac"));

    }

    @Test
    public void testRocketMQ() {
        String c0 = "77010315323266";
        String c1 = "7701041601373766";
        String c2 = "7701021906";
        ExecuteTask.parseLocalCmd(c0, "127.0.0.1");
        ExecuteTask.parseLocalCmd(c1, "127.0.0.1");
        ExecuteTask.parseLocalCmd(c2, "127.0.0.1");
//        for (int i = 0; i < 10; i++) {
//            Map map = new HashMap();
//            map.put("topic", "topic_test");
//            map.put("message", "Just is test messages " + i);
//            ProducerService.pushMsg(Topics.CONSOLE_TOPIC.getTopic(),"Just is test messages " + i);
//        }
    }
}
