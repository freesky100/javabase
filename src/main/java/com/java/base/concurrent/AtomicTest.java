package com.java.base.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 测试本身的锁
 * Created by yw on 2018/5/3.
 */
public class AtomicTest {

    private AtomicInteger integer = new AtomicInteger(0);
    private static CountDownLatch lock = new CountDownLatch(2);

    public int increment(){
        for (int i = 0; i < 10000; i++) {
             integer.incrementAndGet();
        }
        System.out.println(Thread.currentThread().getName()+":"+integer);
        lock.countDown();
        return integer.intValue();
    }

    public int decrement(){
        for (int i = 0; i < 10000; i++) {
            integer.decrementAndGet();
        }
        lock.countDown();
        System.out.println(Thread.currentThread().getName()+":"+integer);
        return integer.intValue();
    }

    public int getVal(){
        return integer.intValue();
    }

    public static void main(String[] args) throws InterruptedException {
       final  AtomicTest test = new AtomicTest();
        //2个线程都调用相加，结果应该是20000
        new Thread(()->test.increment(),"t1").start();
        new Thread(()->test.increment(),"t2").start();
        AtomicTest.lock.await();
        System.out.println(test.getVal());

    }

}
