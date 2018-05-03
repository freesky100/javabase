package com.java.base.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 一组线程等待某一个线程执行完毕后并发执行后续操作
 *
 *
 * Created by yw on 2018/4/26.
 */
public class CyclicBarrierLock {
    private static CyclicBarrier lock;
    private Boss boss = new Boss();

    public CyclicBarrierLock( int total) {
        this.lock = new CyclicBarrier(total,boss);;
        System.out.println("准备开会，参会人数："+total);
    }

    public void reset(int num,int lastNum){
        System.out.println("临时同时，需要与会人数增加到"+num+"还需加入："+(num-lastNum));
        lock.reset();
        this.lock = new CyclicBarrier(num,boss);
    }

    /**
     * 都在等待大领导驾临
     */
    static  class Boss implements Runnable{

        @Override
        public void run() {
                System.out.println("领导驾临，请各个部门成员鼓掌欢迎");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("欢迎领导讲话");
        }
    }

    static class Employee implements Runnable{
        String name;

        public Employee(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                int timelast = new Random().nextInt(7);
                TimeUnit.SECONDS.sleep(timelast);
                System.out.println(Thread.currentThread().getName()+"，"+timelast+"秒后，--"+name+"我已经入座了，等待领导到来，还需要等待:"+lock.getParties()+"人");
                try {
                    lock.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"大会进行中，开1个小时...");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final int count = 5;
        CyclicBarrierLock lock = new CyclicBarrierLock(count);
        for(int i=0;i<count;i++){
            if(i==4){
               lock.reset(10,count);
                break;
            }
            new Thread(new Employee("我是："+i)).start();
//            Thread.sleep(1000);
        }

        for(int i = count;i<(10-count+6);i++){
            new Thread(new Employee("我是"+ i)).start();

        }

    }
}
