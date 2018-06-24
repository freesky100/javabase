package com.java.base.socket.aio;

import com.java.base.socket.bio.Server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
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

/**
 * 异步处理消息
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsynchronousServer> {


    @Override
    public void completed(AsynchronousSocketChannel result, AsynchronousServer attachment) {
        //当有下一个客户端进入的时候,反复调用
        attachment.getServer().accept(attachment,this);
        read(result);
    }

    private void read(AsynchronousSocketChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                //进行读取操作
                attachment.flip();
                System.out.println("Server,收到客户端数据长度："+result);
//                byte[] TwoByte2HexOrReverse = new byte[attachment.remaining()];
//                attachment.get(TwoByte2HexOrReverse);
                String res = new String(attachment.array()).trim();
                System.out.println("客户端内容："+res);
                String response = "服务器响应，收到客户端数据："+res;
                write(channel,res);

            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel channel, String res) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(res.getBytes());
        buffer.flip();
        try {
            channel.write(buffer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousServer attachment) {
        exc.printStackTrace();
    }
}
