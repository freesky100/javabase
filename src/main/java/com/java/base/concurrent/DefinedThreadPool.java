package com.java.base.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 有序队列
 * core线程数有先创建，如果不够则按最大创建。如果队列和最大都满了，抛出异常
 * Created by yw on 2018/4/26.
 */
public class DefinedThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3));

        try {
            //现放入1个，观察现象-->使用默认corePool执行
            pool.execute(new MyTask(1,"task1"));
            //放入2个，观察现象-->添加入队列等待第一个线程结束
            pool.execute(new MyTask(2,"task2"));
            //放入5个观察现象-->5个的时候发现，队列3个满了，corePool1个，这时候maxPool起作用，启动2个线程
            pool.execute(new MyTask(3,"task3"));
            pool.execute(new MyTask(4,"task4"));
            pool.execute(new MyTask(5,"task5"));
            //放入6个观察现象-->抛出RejectedExecutionException异常，线程挂起()
            pool.execute(new MyTask(6,"task6"));
//        pool.execute(new MyTask(7,"task7"));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("拒绝请求。。。");
        }
//        finally{
            pool.shutdown();
//        }






    }



}

class MyTask implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"--"+id+"任务执行");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName()+"--"+id+"任务执行结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int id;
    private String name;

    public MyTask(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
