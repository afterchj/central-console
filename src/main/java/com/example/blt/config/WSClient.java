package com.example.blt.config;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.controller.MainController;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;

/**
 * Created by chenhao.lu on 2019/7/4.
 */
public class WSClient extends WebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(WSClient.class);


    public WSClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info("------ MyWebSocket onOpen ------");
    }

    @Override
    public void onMessage(String s) {
//        logger.info("-------- 接收到服务端数据： " + s + "--------");
//        try {
//             JSONObject jsonStr = JSONObject.parseObject(s);
//            if (jsonStr.get("method") != null) {
//                MainController mainController = new MainController();
//                if("sendSocket4".equals(jsonStr.get("method"))) {
//                    mainController.sendSocket4(String.valueOf(jsonStr.get("host")), String.valueOf(jsonStr.get("command")));
//                }else if("sendSocket5".equals(jsonStr.get("method"))){
//                    mainController.sendSocket5(String.valueOf(jsonStr.get("host")), String.valueOf(jsonStr.get("command")));
//                }
//            }
//        } catch (Exception e) {
//            logger.info("-------- 无法解析： " + s + "--------");
//        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        logger.info("------ MyWebSocket onClose ------{}", i);
    }

    @Override
    public void onError(Exception e) {
        logger.info("------ MyWebSocket onError ------{}", e);
    }


}
