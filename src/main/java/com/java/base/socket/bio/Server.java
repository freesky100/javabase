package com.java.base.socket.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
 * Created by yw on 2018/5/3.
 */
public class Server {
    public static void main(String[] args) {
        Socket s=null;
        try {
            ServerSocket socketServer = new ServerSocket(6666);
            System.out.println("启动服务器....");
            while(true) {
                s = socketServer.accept();
                System.out.println("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                //读取客户端发送来的消息
                String mess = br.readLine();
                System.out.println("客户端：" + mess);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                bw.write(mess + "\n");
                bw.flush();
                System.out.println("发送完成");
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(s!=null){
                try {
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("异常了，");

        }finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
