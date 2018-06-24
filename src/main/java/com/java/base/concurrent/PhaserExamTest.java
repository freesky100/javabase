package com.java.base.concurrent;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

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
public class PhaserExamTest {

    public static void main(String[] args) {

        Student[] students = new Student[5];
        myPhaser myPhaser = new PhaserExamTest.myPhaser();
        for (int i = 0; i < 5; i++) {
           students[i] = new Student(myPhaser);
            myPhaser.register();
        }

        Thread[] threads = new Thread[students.length];
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i], "Student "+i);
            threads[i].start();
        }

        //等待所有线程执行结束
        for (int i = 0; i < students.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Phaser has finished:"+myPhaser.isTerminated());



    }





    static class myPhaser extends Phaser{

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase){
                case 0:
                    return sign();
                case 1:
                   return doNumOne();
                case 2:
                    return doNumTwo();
                case 3:
                    return finish();
                  default:
                      return true;
            }
        }

        private boolean finish() {
            System.out.println("考试结束");
            return true;
        }

        private boolean doNumTwo() {
            System.out.println("第二道题答题完毕");
            return false;
        }

        private boolean doNumOne() {
            System.out.println("第一道题答题完毕");
            return false;
        }

        private boolean sign() {
            System.out.println("学生到期了，已经准备好了，总计人数"+getArrivedParties());
            return false;
        }
    }

    static class Student implements Runnable{

        private myPhaser phaser;

        public Student(myPhaser myPhaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"到达考试");
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName()+"做第1题时间...");
            doExercise1();
            System.out.println(Thread.currentThread().getName()+"做第1题完成...");
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName()+"做第2题时间...");
            doExercise2();
            System.out.println(Thread.currentThread().getName()+"做第2题完成...");
            phaser.arriveAndAwaitAdvance();

        }


        private void doExercise1() {
            long duration = (long)(Math.random()*10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void doExercise2() {
            long duration = (long)(Math.random()*10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
