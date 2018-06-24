package com.java.base.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by yw on 2018/4/25.
 */
public class BlockArrayQueue {

    private ArrayBlockingQueue<Integer> queue;

    public BlockArrayQueue(int maxSize) {
        queue = new ArrayBlockingQueue<>(maxSize);
    }

    private void put(int i)  {
//        queue.add(i);异常
        try {
            queue.put(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "放入元素：" + i);

    }

    private int get() {

//        int i = queue.poll();异常
        int i = 0;
        try {
            i = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "移除元素：" + i);
        return i;
    }

    public static void main(String[] args) {

        BlockArrayQueue queue = new BlockArrayQueue(5);

        //队列存在大小,满抛出异常
        for (int i = 0; i < 5; i++) {
//            queue.put(i);
                queue.put(i);
        }

        //测试阻塞
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue.put(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue.get();
                try {
                    TimeUnit.MILLISECONDS.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
