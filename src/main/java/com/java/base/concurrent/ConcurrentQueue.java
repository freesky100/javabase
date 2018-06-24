package com.java.base.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * 无阻塞的队列
 * ConcurrentLinkedQueue也是同样使用了CAS指令，但其性能并不高因为太多CAS操作。其源码如下：
 * 高并发环境下要实现高吞吐量和线程安全，两个思路：一个是用优化的锁实现，一个是lock-free的无锁结构。
 * 但非阻塞算法要比基于锁的算法复杂得多。开发非阻塞算法是相当专业的训练，而且要证明算法的正确也极为困难，
 * 不仅和具体的目标机器平台和编译器相关，而且需要复杂的技巧和严格的测试。虽然Lock-Free编程非常困难，
 * 但是它通常可以带来比基于锁编程更高的吞吐量。
 * 所以Lock-Free编程是大有前途的技术。它在线程中止、优先级倒置以及信号安全等方面都有着良好的表现。
 * Created by yw on 2018/4/25.
 */
public class ConcurrentQueue {

    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();

    private void add(String string){
        queue.offer(string);
    }

    private void get(){
       String res = (String) queue.poll();
        System.out.println(Thread.currentThread().getName()+"："+res);
    }

    public static void main(String[] args) {
    final ConcurrentQueue queue = new ConcurrentQueue();
        new Thread(()->{
            for(int i=0;i<1000;i++){
                queue.add("我是"+i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<100;i++){
                queue.get();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("end");

    }






}
