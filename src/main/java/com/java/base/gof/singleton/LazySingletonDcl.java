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

import java.util.Random;

/**
 * DCL（Double Check Lock)
 * 懒汉式多线程单例问题
 */
public class LazySingletonDcl {

    private int num=0;

    private LazySingletonDcl() {
//        try {
        System.out.println(Thread.currentThread().getName()+"初始化");
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        this.num = new Random().nextInt(1000)+1;

    }

    //此处如果不用volatile修饰的话，会出现属性初始化不完成问题(目标是调用getNum，由于没有happenbefore)[试验代码没出来效果]
    //synchronized本身保证了有序、原子性、可见性
    //volatile 保证了顺序性（禁止重排序），内存可见性，但不保证原子性
    //对于反射和序列化不能保证
        private static LazySingletonDcl lazySingletonDcl;
//    private static volatile LazySingletonDcl lazySingletonDcl;

    public static LazySingletonDcl getInstance(){
        if(lazySingletonDcl==null)
            System.out.println(Thread.currentThread().getName()+"访问程序");
            synchronized (LazySingletonDcl.class) {
                if (lazySingletonDcl == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lazySingletonDcl = new LazySingletonDcl();
                }
                System.out.println(Thread.currentThread().getName()+"访问程序结束");
            }

        return lazySingletonDcl;
    }

    private int getNum(){
        return this.num;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                LazySingletonDcl singleton=LazySingletonDcl.getInstance();
//                System.out.println(singleton.num);
                System.out.println(Thread.currentThread().getName()+":"+singleton.getNum()+"---"+singleton.hashCode());
            },"t"+i).start();

        }
    }
}

