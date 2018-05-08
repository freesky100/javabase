package com.java.base.socket.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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
 * Created by yw on 2018/5/8.
 */
public class AsynchronousClient implements Runnable {

    private AsynchronousSocketChannel channel;


    public AsynchronousClient() throws IOException {
        channel = AsynchronousSocketChannel.open();
        connect();
    }

    public void connect(){
        channel.connect(new InetSocketAddress("127.0.0.1",6668));
    }

    public void write(String request){
        try {
            channel.write(ByteBuffer.wrap(request.getBytes("utf-8"))).get();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        read();
    }

    private void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel.read(buffer).get();
            buffer.flip();
            byte[] res = new byte[buffer.remaining()];
            buffer.get(res);
            System.out.println("客户端读取到响应："+new String(res,"utf-8"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //client别停掉
            while(true){
                try {
                    Thread.sleep(1000);
                    System.out.println(new Date());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }


    public static void main(String[] args) throws IOException {
        final AsynchronousClient client = new AsynchronousClient();
        final AsynchronousClient client1 = new AsynchronousClient();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(client,"t1").start();
        new Thread(client1,"c2").start();

            client.write("发出："+new Random().nextInt(1000));
            client1.write("发出2："+new Random().nextInt(10000));



    }

}
