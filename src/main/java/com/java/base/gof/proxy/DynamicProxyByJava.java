package com.java.base.gof.proxy;

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
public class DynamicProxyByJava  implements InvocationHandler{

    private ISay iSay;


    public Object bind(ISay iSay){

        this.iSay = iSay;
        Class clazz = iSay.getClass();
        //通过反射机制，创建一个代理类对象实例并返回。用户进行方法调用时使用
        //创建代理对象时，需要传递该业务类的类加载器
        // （用来获取业务实现类的元数据，在包装方法是调用真正的业务方法）、接口、handler实现类
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(),clazz.getInterfaces(),this);
        return o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("我是链接，我可以帮找房子");
        method.invoke(iSay,args);
        System.out.println("已经帮您找到房子了");
        return null;
    }
}
