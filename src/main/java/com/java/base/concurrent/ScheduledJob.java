package com.java.base.concurrent;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 延迟N秒后，每隔N秒执行一次
 * Created by yw on 2018/4/26.
 */
public class ScheduledJob {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleWithFixedDelay(()->{
            System.out.println("当前时间："+new Date());
        }, 3, 1, TimeUnit.SECONDS);
        System.out.println("当前时间start："+new Date());

    }

}
