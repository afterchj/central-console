package com.example.blt.controller;

import com.example.blt.utils.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@RestController
public class MainController {

    private Logger logger= LoggerFactory.getLogger(MainController.class);
    @RequestMapping("/switch")
    public String console(String cmd){
        logger.info("cmd="+cmd);
        SocketUtil.sendCmd(cmd);
        return "ok";
    }
}
