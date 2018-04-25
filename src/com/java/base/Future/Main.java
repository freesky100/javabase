package com.java.base.Future;


/**
 * 异步请求模拟
 * Created by yw on 2018/4/25.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("请求开始...");
        FutureClient futureClient = new FutureClient();
        Data data = futureClient.request("id=1");//null
        System.out.println("请求发送成功");
        System.out.println("做其他的事情");

        String res = data.getRequest();//等待
        System.out.println("res:"+ res);


    }
}
