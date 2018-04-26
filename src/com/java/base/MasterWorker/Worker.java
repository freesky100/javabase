package com.java.base.MasterWorker;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by yw on 2018/4/26.
 */
public class Worker implements   Runnable {
    private ConcurrentHashMap<String,Object> map ;
    private ConcurrentLinkedQueue<Task> queue;


    public Worker() {
    }

    /**
     * 处理task任务
     */
    @Override
    public void run() {
       while(true){
           Task task = queue.poll();
           if(task ==null){
               System.out.println("任务完成");
               break;
           }
           //处理任务
           Object output = handle(task);
           map.put(String.valueOf(task.getId()),output);
           System.out.println("处理任务task:"+task);
       }

    }

    /**
     * 任务处理
     * @param task
     * @return
     */
    private Object handle(Task task) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getPrice();
    }

    public void setMap(ConcurrentHashMap<String, Object> map) {
        this.map = map;
    }

    public void setQueue(ConcurrentLinkedQueue<Task> queue) {
        this.queue = queue;
    }
}
