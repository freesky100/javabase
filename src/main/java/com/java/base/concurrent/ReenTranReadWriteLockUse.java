package com.java.base.concurrent;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 读写分离锁常用在读多写少的场景
 * 如果写多的话建议还是使用ReentransLock
 *
 * Created by yw on 2018/5/2.
 */
public class ReenTranReadWriteLockUse {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Random random = new Random();
    volatile int i =0;

    private void method1() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "Read something->"+i);
            Thread.sleep(random.nextInt(2000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() +"Read ended....");
            lock.readLock().unlock();
        }
    }

    private void method2(){
        try {
            lock.writeLock().lock();
            i++;
            System.out.println(Thread.currentThread().getName()+"Write something i->"+i);
            Thread.sleep(random.nextInt(2000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName()+"Write ended....");
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReenTranReadWriteLockUse lockUse = new ReenTranReadWriteLockUse();

        /**
         * 读读相容
         * 读写互斥
         * 写写互斥
         */
        Thread t1 = new Thread(()->{
            lockUse.method1();
        },"t1");

        Thread t2 = new Thread(()->{
            lockUse.method2();
        },"t2");

        Thread t3 = new Thread(()->{
            lockUse.method1();
        },"t3");
        Thread t4 = new Thread(()->{
            lockUse.method2();
        },"t4");
        //读写不能并行
//        t1.start();
//        t2.start();
        //读读可以并行
//        t1.start();
//        t3.start();
        //写写不能并行
        t1.start();
        t4.start();




    }

}
