package com.java.base.socket.bio;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
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
public class SocketServerPlus {

    private ServerSocket serverSocket;
    private ExecutorService service = Executors.newFixedThreadPool(10);

    private int port = 6667;

//    private InetAddress address = InetAddress.getLocalHost();

    public SocketServerPlus() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("创建服务器成功。。。" + serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            service.shutdown();
            System.out.println("创建服务器失败。。。");
        }
    }
        public void start() throws IOException {
            System.out.println("start...");
            Socket socket = serverSocket.accept();
            service.submit(new ServerHandlerPlus(socket));
        }

    public static void main(String[] args) throws IOException {
        SocketServerPlus plus = new SocketServerPlus();
        plus.start();
    }
}
