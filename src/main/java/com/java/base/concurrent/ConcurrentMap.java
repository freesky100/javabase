package com.java.base.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 并发map
 * 实现最大16个segment
 * Created by yw on 2018/4/25.
 */
public class ConcurrentMap {

    private ConcurrentHashMap<String,Object> map = new ConcurrentHashMap();
    private ConcurrentMap() {
    }

    private void add(String key,String value){
       map.put(key,value);
        System.out.println(Thread.currentThread().getName()+":map放入元素："+key);
    }

    private void get(String key){
        map.get(key);
        System.out.println(Thread.currentThread().getName()+":map取出元素:"+key);
    }


    public static void main(String[] args) {
        ConcurrentMap conurrenMap = new ConcurrentMap();
        new Thread(()->{
            for(int i = 0;i<100;i++){
                conurrenMap.add(String.valueOf(i),"数字:"+i);
            }
        },"t1").start();

        new Thread(()->{
//            try {
////                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for(int i = 0;i<100;i++){
                conurrenMap.get(String.valueOf(i));
            }
        },"t2").start();




    }


}
