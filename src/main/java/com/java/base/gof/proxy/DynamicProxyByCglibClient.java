package com.java.base.gof.proxy;

/**
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 * Created by yw on 2018/5/21.
 */

//  cglib是针对类来实现代理的，原理是对指定的业务类生成一个子类，
// 并覆盖其中业务方法实现代理。因为采用的是继承，所以不能对final修饰的类进行代理。
public class DynamicProxyByCglibClient {

    public static void main(String[] args) {
        BuyerClass buyerClass = new BuyerClass();
        BuyerClass cglib = (BuyerClass) new DynamicProxyByCglib().bind(buyerClass);
        cglib.say();
    }


}
