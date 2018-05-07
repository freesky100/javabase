package com.java.base.gof.singleton;

import com.sun.imageio.plugins.common.LZWCompressor;

import java.util.Random;

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
 * 懒汉式写法及对应的说明
 */
public class LazySingleton {

    private static LazySingleton  lazySingleton= null;

    private int num=0;
    public LazySingleton() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.num = new Random().nextInt(1000);
    }

    /**
     * 单线程没问题，多线程容易实例化多个对象
     * 对于反射和序列化不能保证
     * @return
     */
    public static LazySingleton getInstance(){
        if(lazySingleton==null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }

    public static void main(String[] args) {

        for(int i=0;i<100;i++){
            new Thread(()->{
                LazySingleton singleton =  LazySingleton.getInstance();
                System.out.println(Thread.currentThread().getName()+":"+singleton.num+"---"+singleton.hashCode());
            },"t"+i).start();
        }
    }

}
