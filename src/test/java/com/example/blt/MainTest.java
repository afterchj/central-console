package com.example.blt;

import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.ConsoleUtil;
import com.example.blt.utils.SpringUtils;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Map map = new HashMap();
//        ConsoleUtil.saveHosts(list);
        Set<Map> list = ConsoleUtil.persistHosts();
        map.put("list", list);
        logger.info("list.size=" + list.size());
        List<Map> list1 = sqlSessionTemplate.selectList("console.selectIn", map);
        logger.info("list1.size=" + list1.size());
    }

    @Test
    public void testRemoveKey() {
        Set<Map> list = ConsoleUtil.persistHosts();
    }
}
