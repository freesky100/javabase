package com.java.base.temp;

import java.util.concurrent.Callable;

public class RetryUtils {
    public RetryUtils() {
    }

    public static <V extends RetryUtils.ResultCheck> V retryOnException(int retryLimit, Callable<V> retryCallable) {
        V v = null;

        for(int i = 0; i < retryLimit; ++i) {
            try {
//                v = (RetryUtils.ResultCheck)retryCallable.call();
            } catch (Exception var5) {
                var5.printStackTrace();
                System.out.println("retry on " + (i + 1) + " times v = " + (v == null?null:v.getJson()));
            }

            if(null != v && v.matching()) {
                break;
            }

            System.out.println("retry on " + (i + 1) + " times but not matching v = " + (v == null?null:v.getJson()));
        }

        return v;
    }

    public static <V extends RetryUtils.ResultCheck> V retryOnException(int retryLimit, long sleepMillis, Callable<V> retryCallable) throws InterruptedException {
        V v = null;

        for(int i = 0; i < retryLimit; ++i) {
            try {
//                v = (RetryUtils.ResultCheck)retryCallable.call();
            } catch (Exception var7) {
                System.out.println("retry on " + (i + 1) + " times v = " + (v == null?null:v.getJson()));
            }

            if(null != v && v.matching()) {
                break;
            }

            System.out.println("retry on " + (i + 1) + " times but not matching v = " + (v == null?null:v.getJson()));
            Thread.sleep(sleepMillis);
        }

        return v;
    }

    public interface ResultCheck {
        boolean matching();

        String getJson();
    }
}
