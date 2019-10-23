package com.example.blt.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hongjian.chen on 2018/2/18.
 */

public class EchoClient {

    private static Logger logger = LoggerFactory.getLogger(EchoClient.class);
    private final static String HOST = "127.0.0.1";
    private final static int PORT = 8001;
    private Socket socket;

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);
    }

    public void sendCron(String msg) {
        PrintWriter pw = null;
        try {
            socket = new Socket(HOST, PORT);
            pw = getWriter(socket);
            pw.println(msg);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            pw.close();
            try {
                socket.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
