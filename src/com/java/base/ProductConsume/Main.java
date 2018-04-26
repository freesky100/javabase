package com.java.base.ProductConsume;

import java.util.concurrent.*;

/**
 * Created by yw on 2018/4/26.
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {

    LinkedBlockingQueue<MessageData> queue = new LinkedBlockingQueue<>();
    Product p1 = new Product(queue);
    Product p2 = new Product(queue);
    Product p3 = new Product(queue);

    Consume c1 = new Consume(queue);
    Consume c2 = new Consume(queue);
    Consume c3 = new Consume(queue);

    ExecutorService pool = Executors.newCachedThreadPool();
    pool.submit(p1);
    pool.submit(p2);
    pool.submit(p3);
    pool.submit(c1);
    pool.submit(c2);
    pool.submit(c3);

    Thread.sleep(3000);
    p1.stop();
    p2.stop();
    p3.stop();
//
//    Thread.sleep(1000);
//    c1.stop();
//    c2.stop();
//    c3.stop();
    System.out.println("end");
//    pool.shutdown();

    }



}
