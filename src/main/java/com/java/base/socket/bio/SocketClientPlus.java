package com.java.base.socket.bio;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;

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
public class SocketClientPlus {

    private int port = 6666;
    private Socket socket;
    private BufferedReader inputStream;
//    private BufferedWriter outputStream;
    private PrintWriter outputStream;

    public SocketClientPlus(int port) {
        this.port = port;
        try {
            socket = new Socket("localhost",port);
//            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建客户端失败");
        }
    }

    private void send(String msg)  {
        try {
            outputStream = new PrintWriter(socket.getOutputStream());
            outputStream.write(msg);
            outputStream.flush();
            socket.shutdownOutput();
            System.out.println("发送消息:"+msg);
            String res = recv();
            System.out.println("收到消息:"+res);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private String recv(){
       String resStr =null;
        try {
            System.out.println("recvb msg");
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            resStr = inputStream.readLine();
            System.out.println(resStr);
            if (!resStr.equalsIgnoreCase("bye")){
                System.out.println("收到响应："+resStr);
//                resStr = inputStream.readLine();
            }
//            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resStr;
    }

    private void close(){

            try {
                if(inputStream!=null)
                inputStream.close();
                if(outputStream!=null)
                    outputStream.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        SocketClientPlus  clientPlus = new SocketClientPlus(6666);
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(System.in));
        String send = null;
        try {
            send = bufferedReader.readLine();
            while(!send.equalsIgnoreCase("bye")) {
                clientPlus.send(send);
                send = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
