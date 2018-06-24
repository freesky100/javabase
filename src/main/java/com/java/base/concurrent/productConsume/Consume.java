package com.java.base.concurrent.productConsume;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by yw on 2018/4/26.
 */
public class Consume implements Runnable {

    private BlockingQueue queue;

    private boolean isRunning = true;

    public Consume(BlockingQueue queue) {
        this.queue = queue;
    }

    private Random random = new Random();

    @Override
    public void run() {
        while(isRunning){

            try {
                Thread.sleep(random.nextInt(2000));
                 MessageData data = (MessageData) queue.take();
                System.out.println(Thread.currentThread().getName()+"消费当前对象："+data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        System.out.println("end Consume");
    }

    public void stop(){
        isRunning=false;
    }
}
