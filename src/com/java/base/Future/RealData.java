package com.java.base.Future;

/**
 * Created by yw on 2018/4/25.
 */
public class RealData implements Data {

    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    /**
     * 大数据操作，耗时比较长
     * @param queryString
     */
    public RealData(String queryString) {
        System.out.println("收到参数："+queryString);
        try {
            System.out.println("程序处理中....");
            Thread.sleep(5000);
            result = "处理成功";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRequest() {
        return result;
    }
}
