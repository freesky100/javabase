package com.java.base.gof.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

/**
 * 反射及序列化
 */
public class LazySingletonLockReflect{


    private static boolean flag = false;

    /**
     * 用来保证反射的时候不会初始化2个对象
     */

    private LazySingletonLockReflect() {
       synchronized (LazySingletonLockReflect.class){
           if(flag==false){
               flag =!flag;
           }else{
               throw new RuntimeException("已经创建实例");
           }
       }
    }

    static class LazySingletonHolder{
        private static final LazySingletonLockReflect singleton = new LazySingletonLockReflect();
    }

    private LazySingletonLockReflect getInstance(){
        return LazySingletonHolder.singleton;
    }


    public static void main(String[] args) {

        LazySingletonLockReflect l1=null;
        LazySingletonLockReflect l2=null;
        try {
            Constructor<LazySingletonLockReflect> con = LazySingletonLockReflect.class.getDeclaredConstructor();
//            con.setAccessible(true);
             l1 = con.newInstance();
             l2 = con.newInstance();
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
        }finally {
            System.out.println(l1);
            System.out.println(l2);
        }

    }

}
