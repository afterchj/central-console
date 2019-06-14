package com.example.blt;

import com.example.blt.entity.HostInfo;
import com.example.blt.service.ConsoleService;
import com.example.blt.service.HostService;
import com.example.blt.utils.SpringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by hongjian.chen on 2019/5/31.
 */
public class MainTest {

    private static Logger logger = LoggerFactory.getLogger(MainTest.class);

    public static void main(String[] args) {
        ConsoleService consoleService = (ConsoleService) SpringUtil.getBean(ConsoleService.class);
        logger.warn("id=" + consoleService.getByIp("192.168.56.1").getId());
    }

    @Test
    public void testList() {
        HostService hostService = (HostService) SpringUtil.getBean(HostService.class);
        logger.warn("size=" + hostService.getAll().size());
    }

    @Test
    public void testQuery1() {
        EntityManager em = SpringUtil.getEntityManager().createEntityManager();
        try {
//            Query query = em.createQuery("from HostInfo as p where p.ip = ?1", HostInfo.class);
            Query query = em.createQuery("from HostInfo", HostInfo.class);
//            query.setParameter(1, "127.0.0.1");
//            HostInfo info = (HostInfo) query.getSingleResult();
            List<HostInfo> list=query.getResultList();
            logger.warn("size=" + list.size());
//            System.out.println("id=" + info.getId());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    public void testQuery2() {
        EntityManager em = SpringUtil.getEntityManager().createEntityManager();
        try {
            Query query = em.createQuery("from HostInfo group by ip", HostInfo.class);
            List<HostInfo> info = query.getResultList();
            System.out.println("id=" + info.size());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
