package com.example.blt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hongjian.chen on 2019/2/15.
 */

public class SocketUtil {

   private static Logger logger= LoggerFactory.getLogger(SocketUtil.class);

    /**
     * @param cmd
     */
    public static void sendCmd(String cmd) {
        Socket client = null;
        PrintWriter pw =null;
        try {
            client = new Socket("127.0.0.1", 8001);
            pw = new PrintWriter(client.getOutputStream(), true);
            pw.write(cmd);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
            pw.flush();
            pw.close();
            try {
                client.close();
            } catch (IOException e) {
               logger.error(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        sendCmd("hello world");
    }
}