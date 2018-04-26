package com.java.base.MasterWorker;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * Created by yw on 2018/4/26.
 */
public class Master {

    //1：队列存放任务
    ConcurrentLinkedQueue<Task> queue = new ConcurrentLinkedQueue<Task>();
    //2：worker的列表
    Map<String,Thread> workerMap = new HashMap<>(50);
    //3：得有一个存放结果的任务集合,
    ConcurrentHashMap<String,Object> resMap = new ConcurrentHashMap<>(20);


    /**
     * 初始化线程
     */
    public Master(Worker worker,int total) {
            worker.setMap(resMap);
            worker.setQueue(queue);
        for (int i = 0; i < total; i++) {
            workerMap.put("当前编号"+String.valueOf(i), new Thread(worker));
        }
    }

    /**
     * 添加任务
     */
    public void addTask(Task task){
        if(queue.offer(task)){
            System.out.println("添加任务成功,task:"+task+"队列大小:"+queue.size());
        }
    }

    /**
     * 执行任务
     */
    public void execute(){
        for(Map.Entry<String,Thread> entry:workerMap.entrySet()){
            entry.getValue().start();
        }
    }

    /**
     * 确定是否结束
     * @return
     */
    public boolean isComplete(){
        for(Map.Entry<String,Thread> entry:workerMap.entrySet()){
            if(entry.getValue().getState()!=Thread.State.TERMINATED){
                return false;
            }
        }
        return true;
    }


    public ConcurrentLinkedQueue<Task> getQueue() {
        return queue;
    }

    public void setQueue(ConcurrentLinkedQueue<Task> queue) {
        this.queue = queue;
    }

    public Map<String, Thread> getWorkerMap() {
        return workerMap;
    }

    public void setWorkerMap(Map<String, Thread> workerMap) {
        this.workerMap = workerMap;
    }

    public ConcurrentHashMap<String, Object> getResMap() {
        return resMap;
    }

    public void setResMap(ConcurrentHashMap<String, Object> resMap) {
        this.resMap = resMap;
    }
}
