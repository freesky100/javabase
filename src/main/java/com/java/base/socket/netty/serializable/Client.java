package com.java.base.socket.netty.serializable;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.UnsupportedEncodingException;

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
public class Client {

    private EventLoopGroup worker;
    private String host="localhost";
    private int port=6669;

    public void run(){
        worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //netty 5.0
//                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
//                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        //netty 4.0
                        socketChannel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        socketChannel.pipeline().addLast(new ObjectEncoder());
                        //超时自动关闭
//                        socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                        socketChannel.pipeline().addLast(new ClientSerialHandler());
                    }
                });
        try {
            System.out.println("client...");
            ChannelFuture future = bootstrap.connect(host,port).sync();

            // Wait until the connection is closed.
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        new Client().run();
//        String realpath= File.separator+"resource"+File.separator+"001.jpg";
//        System.out.println(realpath);
//        System.out.println(new String(ResourceBundle.getBundle("property").getString("value").getBytes(CharsetUtil.ISO_8859_1),"UTF-8"));
    }
}
