package com.java.base.gof.singleton;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖保佑             永无BUG
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 * Created by yw on 2018/5/7.
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *使用枚举实现的单例模式，不但可以防止利用反射强行构建单例对象，而且可以在枚举类对象被反序列化的时候，保证反序列的返回结果是同一对象。
 * 枚举是饿汉式加载方式
 * 其它的方式需要额外的工作来实现序列化，否则每次反序列化一个序列化的对象时都会创建一个新的实例。
  可以使用反射强行调用私有构造器（如果要避免这种情况，可以修改构造器，让它在创建第二个实例的时候抛异常）。
 */
public enum EnumSingleton {

    INSTANCE("singleton",100,1000);

    private String name;
    private int id;
    private int age;

    EnumSingleton(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    private EnumSingleton getInstance(){
       return  INSTANCE ;
    }


    public static void main(String[] args) {
        try {
            Constructor<EnumSingleton> constructor = EnumSingleton.class.getConstructor();
            EnumSingleton l1 = constructor.newInstance();
            EnumSingleton l2 = constructor.newInstance();
            constructor.setAccessible(true);
            System.out.println(l1==l2);
            System.out.println(l1.hashCode()+","+l2.hashCode());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
