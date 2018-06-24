package com.java.base.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 不可循环共享锁
 * 一个线程，等待一组线程完成后执行后续操作
 * Created by yw on 2018/4/26.
 */
public class CountDownLatchLock {
    private static CountDownLatch countDownLatch;

    public CountDownLatchLock( int total) {
        this.countDownLatch = new CountDownLatch(total);;
        System.out.println("准备开会，参会人数："+total);
    }

    /**
     * boss等待开会
     */
   static  class Boss implements Runnable{

        @Override
        public void run() {
            try {
                countDownLatch.await();
                System.out.println("我是领导，人员已到齐，宣布开会");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                int timelast = new Random().nextInt(5);
                TimeUnit.SECONDS.sleep(timelast);
                System.out.println(Thread.currentThread().getName()+"，等待"+timelast+"秒后，--"+name+"我已经入座了");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
       final int count = 5;
       CountDownLatchLock lock = new CountDownLatchLock(count);
       new Thread(new Boss()).start();
       for(int i=0;i<count;i++){
           new Thread(new Employee("我是："+i)).start();
       }

    }

}
