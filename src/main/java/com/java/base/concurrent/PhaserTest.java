package com.java.base.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

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
 * Created by yw on 2018/5/10.
 */
public class PhaserTest {

    public static void main(String[] args) {
        //创建时，就需要指定参与的parties个数
        int parties = 12;
        //可以在创建时不指定parties
// 而是在运行时，随时注册和注销新的parties
        Phaser phaser = new Phaser();
//主线程先注册一个
//对应下文中，主线程可以等待所有的parties到达后再解除阻塞（类似与CountDownLatch）
        phaser.register();
        ExecutorService executor = Executors.newFixedThreadPool(parties);
        for(int i = 0; i < parties-1; i++) {
            phaser.register();//每创建一个task，我们就注册一个party
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        int i = 0;
                        while (i < 3 && !phaser.isTerminated()) {
                            Thread.sleep(3000);
                            System.out.println(Thread.currentThread().getName()+"Generation:" + phaser.getPhase());
                            //等待同一周期内，其他Task到达
                            //然后进入新的周期，并继续同步进行
                            phaser.arriveAndAwaitAdvance();
                            i++;//我们假定，运行三个周期即可
                        }
                    } catch (Exception e) {

                    }
                    finally {
                        phaser.arriveAndDeregister();
                    }
                }
            });
        }
//主线程到达，且注销自己
//此后线程池中的线程即可开始按照周期，同步执行。
        phaser.arriveAndDeregister();

    }

}
