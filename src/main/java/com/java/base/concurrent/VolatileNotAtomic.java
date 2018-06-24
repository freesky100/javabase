package com.java.base.concurrent;


/**
 *
 * volatile保证排序和可见性（刷新主内存）
 * atomtic 则可以保证原子性即可见性
 * DCL:
 * Created by yw on 2018/4/25.
 */
public class VolatileNotAtomic {

    private static volatile VolatileNotAtomic volatileNotAtomic=null;

    /**
     * 懒汉式注意DCL，恶汉式无所谓
     * 主要是禁止重排序，通过内存屏障实现 。StoreStore ,StoreLoad ,LoadStore,loadLoad
     * @return VolatileNotAtomic
     */
    private static VolatileNotAtomic getInstance(){
        if(volatileNotAtomic==null){
//            synchronized (VolatileNotAtomic.class) {
//                if(volatileNotAtomic==null) {
                System.out.println(Thread.currentThread().getName() + "----init");
                volatileNotAtomic = new VolatileNotAtomic();
//                }
//            }
        }
        return volatileNotAtomic;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Thread t1 = new Thread(()->{
                getInstance();
        },"t1");
        Thread t2 = new Thread(()->{
                getInstance();
        },"t2");
        Thread t3 = new Thread(()->{
                getInstance();
        },"t3");
        Thread t4 = new Thread(()->{
                getInstance();
        },"t4");


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
//        while (t1.isAlive() || t2.isAlive() || t3.isAlive()|| t4.isAlive()) {
//            System.out.println("end");
//        }

    }

}
