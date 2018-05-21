package com.java.base.gof.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

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
public class DynamicProxyByCglib implements MethodInterceptor {

    private Object target;

    public Object bind(Object target){
        this.target= target;
        Enhancer enhancer = new Enhancer();//创建加强器，用来创建动态代理类
        enhancer.setSuperclass(this.target.getClass());//为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(this);
        return enhancer.create();

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("我是链家，我可以帮助找房");
//        Object obj = methodProxy.invoke(target,objects);//invoke第一个参数一定是我们代理类
        Object obj = method.invoke(target,objects);
        System.out.println("我是链家，房子已经找到");
        System.out.println(obj);
        return obj;
    }
}
