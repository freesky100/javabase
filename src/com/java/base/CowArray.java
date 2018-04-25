package com.java.base;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * 替代vector
 * CopyOnWriteSet 代替hashSet
 *
 * 读多写少
 * 写采用复制算法
 * Created by yw on 2018/4/25.
 */
public class CowArray {

    private CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

    private void add(int i){
        list.add(i);
        System.out.println(Thread.currentThread().getName()+"写入："+i);
    }

    private void get(int i){
        list.get(i);
        System.out.println(Thread.currentThread().getName()+"读取："+i);

    }


    public static void main(String[] args) {
        CowArray cowArray =new CowArray();
        new Thread(()->{
            long time1 = System.currentTimeMillis();
            for(int i =0;i<100;i++){
                cowArray.add(i);
            }
            for(int i =100;i<200;i++){
                cowArray.add(i);
            }
            System.out.println(Thread.currentThread().getName()+"耗时:"+(System.currentTimeMillis()-time1)+"ms");
        },"t1").start();


        new Thread(()->{
            long time1 = System.currentTimeMillis();
            for(int i =0;i<100;i++){
                cowArray.get(i);
            }
            for(int i =0;i<100;i++){
                cowArray.get(i);
            }
            for(int i =100;i<200;i++){
                cowArray.get(i);
            }
            for(int i =0;i<100;i++){
                cowArray.get(i);
            }
            System.out.println(Thread.currentThread().getName()+"耗时:"+(System.currentTimeMillis()-time1)+"ms");
        },"t2").start();



    }
}
