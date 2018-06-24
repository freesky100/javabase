package com.java.base.socket.bio;

import java.io.*;
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
public class ServerHandlerPlus implements Runnable{


    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter bufferedWriter;

    public ServerHandlerPlus(Socket socket) {
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
//            bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            bufferedWriter = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String recv(){
        socketNotBeNull();
        String res=null;
        try {

            System.out.println("recv");
            StringBuffer sb = new StringBuffer();
            res = bufferedReader.readLine();
            if(res!=null){
                System.out.println("收到："+res);
                System.out.println("服务器接受："+res);
                send("您说了："+res);
            }
            System.out.println("没有收到信息");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void send(String message){
        socketNotBeNull();
        System.out.println("send msg"+message);;
        try {
            bufferedWriter.write(message);
            bufferedWriter.flush();
//            bufferedWriter.close();
//            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void socketNotBeNull() {
        if(socket==null){
            throw new NullPointerException("socket not be null");
        }
    }

    @Override
    public void run() {
        recv();
    }
}
