package com.java.base.gof.singleton;


import java.io.*;


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
public class LazySingletonLockSerizable implements Serializable{


    private static boolean flag = false;

    private LazySingletonLockSerizable() {
       synchronized (LazySingletonLockSerizable.class){
           if(flag==false){
               flag =!flag;
           }else{
               throw new RuntimeException("已经创建实例");
           }
       }
    }

    static class LazySingletonHolder{
        private static final  LazySingletonLockSerizable singleton = new LazySingletonLockSerizable();
    }

    private LazySingletonLockSerizable getInstance(){
        return LazySingletonHolder.singleton;
    }

    private Object readObject(){
        return getInstance();
    }
    public static void main(String[] args) {


        //序列化
        String fileName = "singleton.obj";
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            LazySingletonLockSerizable singleton = new LazySingletonLockSerizable().getInstance();
            out.writeObject(singleton);
            out.flush();
            //读取
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            LazySingletonLockSerizable singleton2 = (LazySingletonLockSerizable) in.readObject();
            //不读取了，发序列化换成其他方法
            LazySingletonLockSerizable singleton3 = (LazySingletonLockSerizable) singleton.readObject();
            System.out.println(singleton==singleton2);
            System.out.println(singleton==singleton3);
            System.out.println(singleton.hashCode()+","+singleton2.hashCode()+","+singleton3.hashCode());
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
