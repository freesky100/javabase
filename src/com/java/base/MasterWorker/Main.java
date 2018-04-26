package com.java.base.MasterWorker;

import java.util.Map;
import java.util.Random;

/**
 *
 * Master-worker模式
 * master分配任务，worker执行任务
 * Created by yw on 2018/4/26.
 */
public class Main {

    public static void main(String[] args) {

        Worker worker = new Worker();
        Master master = new Master(worker,10);
        int total = 0;//单价相加结果
        for(int i=0;i<100;i++)
        {
            int price= new Random().nextInt(100);
            master.addTask(new Task(i,"线程"+i,price));
            total +=price;
        }
        System.out.println("期待分布式计算结果为:"+total);
        master.execute();

        int lastTotal =0;//看一看多线程运算后结果
        while(true){
            if(master.isComplete()){
                Map<String,Object> map = master.getResMap();
                System.out.println(map);
                for(Map.Entry<String,Object> me :map.entrySet()){
                    int price = (int) me.getValue();
                    lastTotal+=price;
                }
                break;
            }
        }
        System.out.println("分布式计算结果为："+lastTotal+"是否计算正确："+(lastTotal==total));
    }


}
