package com.java.base.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Future 可以返回异步响应结果
 * 异步结果获取是阻塞的
 * Created by yw on 2018/5/2.
 */
public class FutureUse implements Callable<String>{

    String para ;
//   volatile int num =0;//int++ 类型不具备原子性
    AtomicInteger num = new AtomicInteger(0);

    public FutureUse(String para) {
        this.para = para;
    }

    /**
     * 多线程调用，中间值可能出现误差，但是atom保证最终原子性
     * @return
     */
    @Override
    public String call()  {
        long t1 = System.currentTimeMillis();
        String result=null;

//        try {
//            Thread.sleep(3000);

            for (int i = 0; i < 1000000; i++) {
//                 num++;
                 num.incrementAndGet();
            }

            result = Thread.currentThread().getName()+","+para+"处理完成,num:"+num;
        long t2 = System.currentTimeMillis();
        System.out.println("准备返回结果："+result+",执行时间："+(t2-t1)+"ms");

        return result;
    }

    public int getVal(){
        return num.intValue();
    }

    public static void main(String[] args) {

        ExecutorService service= Executors.newFixedThreadPool(2);
        FutureUse futureUse = new FutureUse("param=futrueTask+future");
        FutureTask<String> futureTask = new FutureTask<String>(futureUse);

        //Future ==NULL的时候执行完成，获取结果用futureTask
        service.submit(futureTask);
//        Future future = service.submit(new FutureUse("param=future"));
        Future future = service.submit(futureUse);
        System.out.println("执行任务中。。。");
//        while(true){
//            //获取成功会返回NULL值
//            if(future==null){
//                System.out.println("执行完毕了");
//                break;
//            }
//        }


        service.shutdown();
        System.out.println("执行其他任务");

        try {
//            Thread.sleep(5000);
//            if(future.isDone())
            System.out.println("serverceFuture->"+future.get());//此方法会阻塞到结果返回
//            if(futureTask.isDone())
            System.out.println("serverceFutureTask->"+futureTask.get());

            //
            System.out.println("最终结果："+futureUse.getVal());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



    }
}

