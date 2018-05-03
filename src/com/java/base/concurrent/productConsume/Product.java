package com.java.base.concurrent.productConsume;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yw on 2018/4/26.
 */
public class Product implements Runnable {


    private BlockingQueue<MessageData> queue;

    private boolean isRunning=true;
    private static  AtomicInteger atomicInteger =new AtomicInteger(0);

    private static Random random = new Random();

    public Product(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while (isRunning) {
            try {
                Thread.sleep(random.nextInt(100));
                int id = atomicInteger.incrementAndGet();
                MessageData data = new MessageData(id, "数据" + id);
                if (!queue.offer(data, 100, TimeUnit.MILLISECONDS)) {
                    System.out.println("提交队列失败");
                    continue;
                }
                System.out.println(Thread.currentThread().getName()+"添加数据："+data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end Product");
    }

    public void stop(){
        isRunning=false;
    }
}
