package com.example.blt.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by hongjian.chen on 2019/6/17.
 */
public class ExecuteTask {
    private static Logger logger = LoggerFactory.getLogger(ExecuteTask.class);
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void pingInfo(String msg, String ip) {
        PingTask task = new PingTask(msg, ip);
        FutureTask futureTask = new FutureTask(task);
        executorService.submit(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void pingStatus() {

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {


    }

}
