package com.java.base.temp;

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
 * Created by yw on 2018/5/11.
 */

import java.util.concurrent.Callable;

/**
 * 重试
 *
 * 1：需要传入对象的重试方法
 * 2：可以实现方法的同步或异步（通知类可以新线程同步或异步）
 * 3：可以重试次数及重试时间
 * 4：
 */
public class Retry {

    public <V extends check> V retry(int times, Callable<V> callable){
        V v = null;
        for (int i = 0; i < times; i++) {
            try {
                System.out.println("kanak");
               v =  callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("di:"+i);
            if(null !=v && v.isMatching() ){
                break;
            }
        }
        return v;
    }


    public interface check{
       String getObj();
       boolean isMatching();
    };

    public static void main(String[] args) {

        new Retry().retry(3, new Callable<Student>() {
            @Override
            public Student call() throws Exception {
                System.out.println("电泳我了");
                return new Student(Student.doPost());
            }
        });


    }



}

class Student implements Retry.check{

    private String name;
    private boolean male = true;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String getObj() {
        return this.name;
    }

    @Override
    public boolean isMatching() {
        return this.male==true;
    }

    public static String doPost() throws Exception{
            Thread.sleep(1000);
//            throw new IllegalArgumentException("异常发生");
        return "111";
    }


}
