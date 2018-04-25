package com.java.base;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * 无长度住阻塞队列
 * BlockingQueue 方法以四种形式出现，对于不能立即满足但可能在将来某一时刻可以满足的操作，
 * 这四种形式的处理方式不同：第一种是抛出一个异常，第二种是返回一个特殊值（null 或 false，具体取决于操作），
 * 第三种是在操作可以成功前，无限期地阻塞当前线程，
 * 第四种是在放弃前只在给定的最大时间限制内阻塞。下表中总结了这些方法：
 * 	抛出异常	特殊值	阻塞	超时
 插入	add(e)	offer(e)	put(e)	offer(e, time, unit)
 移除	remove()	poll()	take()	poll(time, unit)
 检查	element()	peek()	不可用	不可用
 *
 * Created by yw on 2018/4/25.
 */
public class BlockLinkedQueue {

    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    private void put(int i){
        queue.add(i);
        System.out.println(Thread.currentThread().getName()+"添加元素:"+i);
    }

    private int get() throws InterruptedException {
//        return queue.poll();
        int remove = queue.poll();
//        int remove= queue.take();
        System.out.println(Thread.currentThread().getName()+"移除元素:"+remove);
        return remove;
    }

    public static void main(String[] args) {
        final BlockLinkedQueue queue = new BlockLinkedQueue();
        new Thread(()->{
            for(int i=0;i<100;i++){
                queue.put(i);
                try {
                    TimeUnit.MILLISECONDS.sleep(300L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0;i<100;i++){
                    try {
                        queue.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }

}
