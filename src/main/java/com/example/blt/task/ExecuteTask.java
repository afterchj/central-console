package com.example.blt.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.config.WebSocket;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.entity.dd.Topics;
import com.example.blt.netty.ClientMain;
import com.example.blt.service.ProducerService;
import com.example.blt.utils.ConsoleUtil;
import com.example.blt.utils.MapUtil;
import com.example.blt.utils.SpringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by hongjian.chen on 2019/6/17.
 */
public class ExecuteTask {
    private static Logger logger = LoggerFactory.getLogger(ExecuteTask.class);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
//    private staticClientMainClientMain =ClientMain();
//    private static RedisTemplate redisTemplate = SpringUtils.getRedisTemplate();

    public static void pingInfo(String ip, String msg) {
        executorService.submit(new PingTask(ip, msg));
    }

    public static void ping(boolean flag, int times, String... host) {
        try {
            for (int i = 0; i < times; i++) {
                logger.warn("times [{}]", i + 1);
                for (String ip : host) {
                    JSONObject object = new JSONObject();
                    object.put("host", ip);
                    object.put("command", "7701011B66");
                    ClientMain.sendCron(object.toJSONString());
                    if (flag) {
                        Thread.sleep(5000);
                        object.put("command", "7701012766");
                        ClientMain.sendCron(object.toJSONString());
                    }
                }
                Thread.sleep(20000);
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public static void parseLocalCmd(String str, String ip) {
        executorService.submit(() -> {
            Map map = new ConcurrentHashMap();
            String prefix = str.substring(0, 8);
            if (str.contains("3232")) {
                map.put("status", 0);
            } else {
                map.put("status", 1);
            }
            map.put("host", ip);
            String cmd = str.substring(prefix.length());
            Integer cid = Integer.parseInt(cmd.substring(0, 2), 16);
            switch (prefix) {
                case "77010416":
                    map.put("ctype", "CW");
                    map.put("x", cmd.substring(2, 4));
                    map.put("y", cmd.substring(4, 6));
                    map.put("cid", cid);
                    break;
                case "77010315":
                    map.put("ctype", "C0");
                    map.put("x", cmd.substring(0, 2));
                    map.put("y", cmd.substring(2, 4));
                    break;
                case "77010219":
                    map.put("ctype", "42");
                    map.put("cid", cid);
                    break;
                default:
                    break;
            }
            String info = JSON.toJSONString(map);
            try {
                ProducerService.pushMsg(Topics.LOCAL_TOPIC.getTopic(), info);
            } catch (Exception e) {
                sqlSessionTemplate.selectOne("console.saveConsole", map);
                WebSocket.sendMessage(info);
            }
        });
    }

    public static String sendCmd(ControlTask task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask(task);
        executor.submit(futureTask);
        executor.shutdown();
        String result;
        try {
            result = futureTask.get();
        } catch (Exception e) {
            result = "fail";
        }
        return result;
    }

    public static void pingStatus(boolean delay, int times) {
        List<String> ips = sqlSessionTemplate.selectList("console.getHosts");
        new Thread(() -> {
            try {
                if (delay) {
                    Thread.sleep(20000);
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            ping(delay, times, ips.toArray(new String[ips.size()]));
        }).start();
    }

    public static void saveInfo(String msg, Map map, Set set, boolean flag) {
        if (msg.indexOf("77040F01") != -1) {
            executorService.submit(() -> {
                MapUtil.removeEntries(map, new String[]{"lmac"});
                set.add(map);
                ConsoleUtil.saveLmac(ConsoleKeys.lMAC.getValue(), set, 5);
            });
        } else if (msg.indexOf("77040F0227") != -1) {
            executorService.submit(() -> {
                MapUtil.removeEntries(map, new String[]{"vaddr"});
                set.add(map);
                ConsoleUtil.saveVaddr(ConsoleKeys.VADDR.getValue(), set, 20);
            });
        }
    }

    public static void translateCmd(String msg) {
        if (msg.indexOf("77010315") != -1) {
            JSONObject object = new JSONObject();
            object.put("host", "all");
            object.put("command", msg);
            ClientMain.sendCron(object.toJSONString());
        }
    }

}
