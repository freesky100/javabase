package com.java.base.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 //                            _ooOoo_
 //                           o8888888o
 //                           88" . "88
 //                           (| -_- |)
 //                            O\ = /O
 //                        ____/`---'\____
 //                      .   ' \\| |// `.
 //                       / \\||| : |||// \
 //                     / _||||| -:- |||||- \
 //                       | | \\\ - /// | |
 //                     | \_| ''\---/'' | |
 //                      \ .-\__ `-` ___/-. /
 //                   ___`. .' /--.--\ `. . __
 //                ."" '< `.___\_<|>_/___.' >'"".
 //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 //                 \ \ `-. \_ __\ /__ _/ .-` / /
 //         ======`-.____`-.___\_____/___.-`____.-'======
 //                            `=---='
 //
 //         .............................................
 //                  佛祖保佑             永无BUG
 //          佛曰:
 //                  写字楼里写字间，写字间里程序员；
 //                  程序人员写程序，又拿程序换酒钱。
 //                  酒醒只在网上坐，酒醉还来网下眠；
 //                  酒醉酒醒日复日，网上网下年复年。
 //                  但愿老死电脑间，不愿鞠躬老板前；
 //                  奔驰宝马贵者趣，公交自行程序员。
 //                  别人笑我忒疯癫，我笑自己命太贱；
 //                  不见满街漂亮妹，哪个归得程序员？
 * Created by yw on 2018/5/3.
 */
public class SocketServer {

    private  static int port=6666;



    public static void main(String[] args) {

        ServerSocket serverSocket =null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server start...");
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("获取请求：" + socket);
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("异常");
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {

        }


    }

}
