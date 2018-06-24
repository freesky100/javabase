package com.java.base.concurrent;

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

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 一个任务分为N个阶段，每个阶段又并发为n个线程，同时希望每个阶段的线程执行完毕后才能执行下一个阶段的任务
 * 推荐使用phaser
 *
 *
 *
 * 例如有这样的一个题目：5个学生一起参加考试，一共有三道题，要求所有学生到齐才能开始考试，
 * 全部同学都做完第一题，学生才能继续做第二题，全部学生做完了第二题，才能做第三题，
 * 所有学生都做完的第三题，考试才结束
 *
 *
 * 在Phaser内有2个重要状态，分别是phase和party。
 *  phase就是阶段，初值为0，当所有的线程执行完本轮任务，同时开始下一轮任务时，
 *  意味着当前阶段已结束，进入到下一阶段，phase的值自动加1。party就是线程，
 *  party=4就意味着Phaser对象当前管理着4个线程。Phaser还有一个重要的方法经常需要被重载，
 *  那就是boolean onAdvance(int phase, int registeredParties)方法。此方法有2个作用：
 *  1、当每一个阶段执行完毕，此方法会被自动调用，因此，重载此方法写入的代码会在每个阶段执行完毕时执行，
 *  相当于CyclicBarrier的barrierAction。
 *  2、当此方法返回true时，意味着Phaser被终止，因此可以巧妙的设置此方法的返回值来终止所有线程。
 *
 */
public class PhaserLock {

    private int parties =3;//每个阶段的并发线程
    private int phasers= 3;//分布几个阶段

    private Phaser phaser = new Phaser(){
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            System.out.println("当前第"+phase+"阶段，总计线程"+getRegisteredParties());
            System.out.println("===============");
            return false;
        }
    };


    public void run() {

        for (int i = 0; i < parties; i++) {
                System.out.println("当前第"+i+"阶段，总计线程"+parties);
                new Thread(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(3000));
                        System.out.println(Thread.currentThread().getName() + "当前线程：" + String.format("Phase %s", phaser));
                        phaser.arriveAndAwaitAdvance();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, "t" + i).start();
            System.out.println("第"+i+"阶段结束");
        }
    }

    public static void main(String[] args) {

        /**
         * phaser 代替CyclicBarrier
         */
        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            MyTask task = new MyTask(phaser);
            new Thread(task,"t"+i).start();
        }



        /**
         * 代替CountDownLatch
         */
        Phaser phaser1 = new Phaser(3);//countdown 2

        for(int i=0;i<3;i++){
            new Thread(new MyTaskCountdown(phaser1),"t"+i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        phaser1.arrive();
        phaser1.arrive();
        phaser1.arrive();
        System.out.println("任务完成。。。");

    }

    /**
     * 自己特性
     *
     */






    static class MyTask implements Runnable{
        private Phaser phaser;

        public MyTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"执行完任务，等待其他任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName()+"继续执行任务");

        }
    }

    static class MyTaskCountdown implements Runnable{
        private Phaser phaser;

        public MyTaskCountdown(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"Countdown执行完任务，等待其他任务");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.awaitAdvance(phaser.getPhase());
            System.out.println(Thread.currentThread().getName()+"Countdown继续执行任务");

        }
    }
}
