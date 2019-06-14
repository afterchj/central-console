package com.example.blt;

import com.example.blt.entity.HostInfo;
import com.example.blt.utils.SpringUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by hongjian.chen on 2019/5/31.
 */
public class MainTest {

    public static void main(String[] args) {

    }

    @Test
    public void testQuery1() {
        EntityManager em = SpringUtil.getEntityManager().createEntityManager();
        try {
            Query query = em.createQuery("from HostInfo as p where p.ip = ?1", HostInfo.class);
            query.setParameter(1, "127.0.0.1");
            HostInfo info = (HostInfo) query.getSingleResult();
            System.out.println("id=" + info.getId());
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
