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
        sqlSessionTemplate.selectOne("console.saveConsole",map);
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
//        String str = "77 04 0F 01 A9 10 64 D7 AC F0 7D 00 00 00 44 4F 03 0A CC CC ".replace(" ","");
//        String str = "77040F01A91064D7ACF07D000000444F030ACCCC";
        String str = "77040F0227E9010000713232000000000000CC";
//        StrUtil.buildLightInfo(str,"127.0.0.1");
        ExecuteTask.pingInfo(str, "127.0.0.1");
        System.out.println(str);
    }

    @Test
    public void testUpdate() {
        List<Map> list = sqlSessionTemplate.selectList("console.getLmac");
        Set<Map> set = new HashSet<>();
        set.addAll(list);
        ConsoleUtil.saveVaddr(ConsoleKeys.VADDR.getValue(), set, 30);
        logger.warn("list=" + set);
//        Map map=new HashMap();
//        map.put("list",set);
//        sqlSessionTemplate.update("console.saveUpdate2", map);
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
        System.out.println(ConsoleUtil.getLightSize(new String[]{}));
        String key1 = ConsoleKeys.VADDR.getValue();
        String key2 = ConsoleKeys.lMAC.getValue();
        ConsoleUtil.saveInfo("test_vaddr", vaddr);
        List set1 = ConsoleUtil.getValueTest("test_vaddr");
        ConsoleUtil.saveInfo("test_lmac", set1);
        List set2 = ConsoleUtil.getValueTest("test_lmac");
//        Set set2 = getInfo(key2);
        logger.warn("lists=" + set1);
        logger.warn("lists=" + set2);
    }

    @Test
    public void testRedis() {
        Set<Map> lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        Set<Map> vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
        logger.warn("lmacSet=" + lmacSet);
        logger.warn("vaddrSet=" + vaddrSet);
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
