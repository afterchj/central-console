package com.example.blt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.entity.dd.Groups;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.utils.ConsoleUtil;
import com.example.blt.utils.PropertiesUtil;
import com.example.blt.utils.SpringUtils;
import com.example.blt.utils.StringBuildUtils;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * Created by hongjian.chen on 2019/5/31.
 */
public class MainTest {

    private static Logger logger = LoggerFactory.getLogger(MainTest.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static RedisTemplate redisTemplate = SpringUtils.getRedisTemplate();

    public static void main(String[] args) {
//        ConsoleService consoleService = (ConsoleService) SpringJpaUtil.getBean(ConsoleService.class);
//        logger.warn("id=" + consoleService.getByIp("192.168.56.1").getId());
//        String arg1 = "770509010606060606060606CCCC";
//        String meshId = arg1.substring(arg1.length() - 12, arg1.length() - 4);
//        System.out.println(meshId);
        String meshId = "";
        for (int i = 0; i < 16; i += 2) {
            meshId += String.valueOf(Integer.parseInt("0200010904080306".substring(i, i + 2), 16));
        }
        System.out.println(meshId);
        System.out.println(1);

    }

    @Test
    public void testList() {
        StringBuilder cmd = new StringBuilder("77010219");
        int sceneId = 1;
        switch (sceneId) {
            case 21:
                cmd = new StringBuilder(Groups.GROUPSA.getOff());
                break;
            case 22:
                cmd = new StringBuilder(Groups.GROUPSA.getOn());
                break;
            default:
                String strHex = Integer.toHexString(sceneId).toUpperCase();
                if (strHex.length() == 1) {
                    cmd.append("0" + strHex);
                } else {
                    cmd.append(strHex);
                }
                break;
        }
        logger.warn("cmd="+cmd.toString());

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
//        String arg1="7705020803CCCC";
//        Map map = new HashMap();
//        map.put("host", "e1753bd4");
//        String flag = arg1.substring(8, 10);
//        map.put("flag", flag);
//        sqlSessionTemplate.update("console.updateHostsFlag", map);
//        String temp = sqlSessionTemplate.selectOne("console.getHost");
//        String hostId = sqlSessionTemplate.selectOne("console.getHostId","81444189");
//        System.out.println(Integer.parseInt("0A", 16));
//        List hosts = sqlSessionTemplate.selectList("console.getHosts");
        String host="03d167ab";
        String host_id = sqlSessionTemplate.selectOne("console.getHost", "00d7ab1f");
        logger.warn("host_id="+host_id);
//        hosts.remove("e1753bd4");
//        List<String> list = sqlSessionTemplate.selectList("console.getAll");
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        for (String id : list) {
//            String tem = valueOperations.get(id, 0, -1);
//            System.out.println(id + "\t" + tem);
//        }
//        list.remove("e1753bd4");
//        System.out.println(list + "\t" + temp);
//        if (hosts.size() > 0) {
//            sqlSessionTemplate.update("console.updateHostsStatus", hosts);
//        }
//        Set set = new HashSet(hosts);
//        map.put("ip", "127.0.0.1");
//        map.put("status", true);
//        sqlSessionTemplate.insert("console.insertHost", map);
//        String str = "77 04 0F 01 A9 10 64 D7 AC F0 7D 00 00 00 44 4F 03 0A CC CC ".replace(" ","");
//        String str = "77040F01A91064D7ACF07D000000444F030ACCCC";
//        String str = "77040F0227E9010000713232000000000000CC";
//        StringBuildUtils.buildLightInfo(str,"127.0.0.1");
//        ExecuteTask.pingInfo("127.0.0.1", str);
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
//        ConsoleUtil.saveInfo(ConsoleKeys.LSIZE.getValue(), 10);

        redisTemplate.opsForValue().set("host_id", "55555555");
        System.out.println(redisTemplate.opsForValue().get("host_id", 0, -1));
//        Set<Map> lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
//        Set<Map> vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
//        logger.warn("lmacSet=" + lmacSet);
//        logger.warn("vaddrSet=" + vaddrSet);
    }

    @Test
    public void testHash() throws InterruptedException {
        List<String> hosts = sqlSessionTemplate.selectList("console.getHosts", "sfd");
        System.out.println(hosts.size());
//        List list = sqlSessionTemplate.selectList("console.getLight", "192.168.10.12");
//        Set set = new HashSet(list);
//        ConsoleUtil.saveLight("t_light", "l_vaddr", "192.168.10.12", set);
//        Map rs = ConsoleUtil.getLight("l_info");
//        Map map = ConsoleUtil.getLight(ConsoleKeys.LINFO.getValue());
//        Integer osize = (Integer) ConsoleUtil.getValue(ConsoleKeys.LSIZE.getValue());
//        Set lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
//        Set vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
//        String host = (String) map.get("ip");
//        Set lmacSet = (Set) map.get(ConsoleKeys.lMAC.getValue());
//        Set vaddrSet = (Set) map.get(ConsoleKeys.VADDR.getValue());
        Map params = new HashMap();
        params.put("ip", "192.168.10.17");
//        params.put("list", vaddrSet);
//        Integer size = sqlSessionTemplate.selectOne("console.selectIn", params);
//        logger.warn("size [{}]", size);
//        Set lmac = (Set) rs.get("l_vaddr");
//        logger.warn("map [{}]", vaddrSet);
//        Thread.sleep(10000);
//        Map rs1 = ConsoleUtil.getLight("t_light");
//        logger.warn("ip [{}] \n lmac [{}]", rs1.get("ip"), rs1.get("lmac"));

    }

    @Test
    public void testRocketMQ() {
//        String c0 = "77010315323266";
//        String c1 = "7701041601373766";
//        String c2 = "7701021906";
//        ExecuteTask.parseLocalCmd(c0, "127.0.0.1");
//        ExecuteTask.parseLocalCmd(c1, "127.0.0.1");
//        ExecuteTask.parseLocalCmd(c2, "127.0.0.1");
        for (int i = 0; i < 10; i++) {
//            Map map = new HashMap();
//            map.put("topic", "topic_test");
//            map.put("message", "Just is test messages " + i);
//            try {
//                ProducerService.pushMsg("user-topic", "Just is test messages " + i);
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
        }
    }

    @Test
    public void testSet() {
        redisTemplate.opsForValue().increment(ConsoleKeys.LTIMES.getValue());
        System.out.println(redisTemplate.opsForValue().get(ConsoleKeys.LTIMES.getValue()));
        Integer temp = (Integer) ConsoleUtil.getValue(ConsoleKeys.TSIZE.getValue());
        int total = temp == null ? -1 : temp;
        logger.warn("temp[{}] total[{}]", temp, total);
//        ConsoleUtil.cleanKey(ConsoleKeys.VADDR.getValue(), ConsoleKeys.HOSTS.getValue(), ConsoleKeys.lMAC.getValue());
//        Set<String> set = new HashSet<>();
//        for (int i = 1; i < 10; i++) {
//            set.add("192.168.16." + i);
//        }
//        for (int i = set.size(); i > 0; i--) {
//            set.remove("192.168.16." + i);
//            logger.warn("set{} size[{}]",set, set.size());
//        }
    }

    @Test
    public void testRedisK() {
        System.out.println(Integer.parseInt("32", 16));
//        redisTemplate.opsForValue().set(ConsoleKeys.LTIMES.getValue(), 3, 10, TimeUnit.MINUTES);
    }

    @Test
    public void testRedisPublish() {
        JSONObject object = new JSONObject();
        object.put("meshId", "00000000");
        object.put("item_set", "1");
        object.put("second", "0/30");
        object.put("week", "重复,周日,周一,周二,周三,周四,周五,周六");
        redisTemplate.convertAndSend("channel_cron", object);
        object.put("meshId", "88888888");
        object.put("second", "0/10");
        object.put("week", "重复,周日,周一,周二");
        System.out.println(object);
        redisTemplate.convertAndSend("channel_cron", object);
    }

    @Test
    public void getWeek() {
        JSONObject object = new JSONObject();
        object.put("meshId", "00000000");
        object.put("item_set", "1");
        object.put("second", "0/30");
        object.put("week", "重复,周日,周一,周二,周三,周四,周五,周六");
        String[] weeks = object.getString("week").split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < weeks.length; i++) {
            switch (weeks[i]) {
                case "周日":
                    weeks[i] = "SUN";
                    break;
                case "周一":
                    weeks[i] = "MON";
                    break;
                case "周二":
                    weeks[i] = "TUE";
                    break;
                case "周三":
                    weeks[i] = "WED";
                    break;
                case "周四":
                    weeks[i] = "THU";
                    break;
                case "周五":
                    weeks[i] = "FRI";
                    break;
                case "周六":
                    weeks[i] = "SAT";
                    break;
            }
            if (i == weeks.length - 1) {
                stringBuilder.append(weeks[i]);
            } else {
                stringBuilder.append(weeks[i] + ",");
            }
        }

        System.out.println(stringBuilder.toString());
    }

    @Test
    public void testSend() {
//        sqlSessionTemplate.update("console.saveHostsStatus");
//        List<CronVo> cronVos = sqlSessionTemplate.selectList("console.getCron");
//        System.out.println(JSON.toJSONString(cronVos) + "\t" + cronVos.size());
//        List<String> hosts = sqlSessionTemplate.selectList("console.getHostsByGid", "45642");
        String host = sqlSessionTemplate.selectOne("console.getMaster", "03d167cb");
        System.out.println("flag=" + "".equals(host));
//        System.out.println(hosts.size() + "\t" + host);
//        JSONObject object = new JSONObject();
//        object.put("host", "master");
//        object.put("command", "77011365FFFFFFFF210D000000521FEA62D7ACF00101CCCC");
//        ClientMain.sendCron(object.toJSONString());
    }

    @Test
    public void testSaveType() {
        String arg1 = "770505060b1a0109CCCC";
        Map map = new HashMap();
        String temp = StringBuildUtils.sortMac(arg1.substring(8, 12)).replace(":", "");
        int product = Integer.parseInt(temp, 16);
        String version = arg1.substring(12, 16);
        map.put("hostId", "0726036a");
        map.put("type", product);
        map.put("version", version);
        sqlSessionTemplate.insert("console.saveUpdateHosts", map);
    }

    @Test
    public void testProperties() {
        String mode = PropertiesUtil.getValue("rocketmq.model");
        System.out.println("mode=" + mode);
    }
}
