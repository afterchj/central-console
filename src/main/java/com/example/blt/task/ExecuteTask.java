package com.example.blt.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.entity.dd.Topics;
import com.example.blt.netty.ClientMain;
import com.example.blt.service.ProducerService;
import com.example.blt.utils.ConsoleUtil;
import com.example.blt.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
//    private staticClientMainClientMain =ClientMain();
//    private static RedisTemplate redisTemplate = SpringUtils.getRedisTemplate();

    public static void pingInfo(String ip, String... msg) {
        PingTask task = new PingTask(ip, msg);
        FutureTask futureTask = new FutureTask(task);
        executorService.submit(futureTask);
        try {
            futureTask.get();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void saveInfo(String msg, Map map, Set set, boolean flag) {
        if (msg.indexOf("77040F01") != -1) {
            executorService.submit(() -> {
                MapUtil.removeEntries(map, new String[]{"lmac"});
                set.add(map);
                ConsoleUtil.saveLmac(ConsoleKeys.lMAC.getValue(), set, 60);
            });
        } else if (msg.indexOf("77040F0227") != -1) {
            executorService.submit(() -> {
                MapUtil.removeEntries(map, new String[]{"vaddr"});
                set.add(map);
                ConsoleUtil.saveVaddr(ConsoleKeys.VADDR.getValue(), set, 30);
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

    public static void pingStatus(boolean delay, int times) {
        new Thread(() -> {
            JSONObject object = new JSONObject();
            object.put("host", "all");
            try {
                if (delay) {
                    new Thread().sleep(20000);
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            for (int i = 0; i < times; i++) {
                object.put("command", "7701011B66");
                ClientMain.sendCron(object.toJSONString());
                try {
                    new Thread().sleep(5000);
                    object.put("command", "7701012766");
                    ClientMain.sendCron(object.toJSONString());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        }).start();
    }

    public static void parseLocalCmd(String str, String ip) {
        executorService.submit(() -> {
            Map map = new ConcurrentHashMap();
            try {
                String prefix = str.substring(0, 8);
                map.put("host", ip);
                map.put("other", str);
                String cmd = str.substring(prefix.length());
                String cid = cmd.substring(0, 2);
                switch (prefix) {
                    case "77010416":
                        map.put("ctype", "C1");
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
                ProducerService.pushMsg(Topics.LOCAL_TOPIC.getTopic(), JSON.toJSONString(map));
            } catch (Exception e) {
                logger.error(e.getMessage());
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

}
