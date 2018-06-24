package com.java.base.socket.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

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
 * Created by yw on 2018/5/4.
 */
public class NioServer implements Runnable {

    private Selector selector;

    private SocketAddress inetAddress = new InetSocketAddress("localhost",6666);

    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    //TODO:回写未实现需要wirte
    /**
     * 构建server
     */
    private  NioServer(){
        //过调用Selector.open()方法创建一个Selector，如下：
        try {
            selector = Selector.open();
            //注册serverChannel
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.bind(inetAddress);
            //设置状态
            SelectionKey key = socketChannel.register(selector,SelectionKey.OP_ACCEPT);
            System.out.println("start server....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 单线程开启server轮训
     */
    @Override
    public void run() {
            while(true){
                try {
                    //开始监听多路
                    int num = selector.select();
                    if(num<1){
                        continue;
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        keys.remove(key);
                        if(key.isValid()){
                            if(key.isAcceptable()){
                                doAccept(key);
                            }
                            if(key.isReadable()){
                                doRead(key);
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * 读取数据s
     * @param key
     */
    private void doRead(SelectionKey key) {

        SocketChannel socketChannel = (SocketChannel) key.channel();
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        try {
            int len = socketChannel.read(buffer);
            while(len>0){
                buffer.flip();
                System.out.println(buffer);
                byte[] readContent = new byte[buffer.limit()];
                buffer.get(readContent);
                System.out.println("接受服务端消息:"+new String (readContent));
                len = socketChannel.read(buffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * accept接受客户端channel，并且注册到selector
     */
    private void doAccept(SelectionKey key) {
        //获取服务通道
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        try {
            //获取客户端通道
           SocketChannel socketChannel =  serverSocketChannel.accept();
           socketChannel.configureBlocking(false);
           socketChannel.register(selector,SelectionKey.OP_READ);
            System.out.println("connect  from "+socketChannel);
//           socketChannel.connect(inetAddress);
//           socketChannel.register(selector,20);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        new Thread(new NioServer()).start();
    }
}
