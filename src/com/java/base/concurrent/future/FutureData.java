package com.java.base.concurrent.future;



/**
 * 包装类
 * Created by yw on 2018/4/25.
 */
public class FutureData implements Data  {

    private RealData realData;

    private boolean isReady= false;


    public synchronized void setRealData(RealData realData) {
        if(isReady) {
            return ;
        }
        this.realData = realData;
        isReady = true;
        notify();//唤醒获取结果线程
    }

    @Override
    public synchronized String getRequest() {
        while(!isReady){
            try {
                wait();//阻塞到此处
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return  realData.getResult();
    }

}
