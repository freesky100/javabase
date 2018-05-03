package com.java.base.concurrent.future;

/**
 * Created by yw on 2018/4/25.
 */
public class FutureClient  {

    private FutureData futureData = new FutureData();
    //请求
    public Data request(String queryStr){
            new Thread(()->{
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }).start();
        return futureData;
    }
}
