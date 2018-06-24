package com.java.base.socket.netty.serializable;


import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;


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
public class ServerSerialHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Req req = (Req)msg;
        try {
            System.out.println("收到响应："+req);
            Res res = new Res();
            res.setId(req.getId());
            res.setName("服务器："+req.getName());
            res.setMsg("服务器响应");
            ctx.writeAndFlush(res);
            ctx.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
//            ctx.writeAndFlush(Unpooled.copiedBuffer("999".getBytes()));
//            ctx.writeAndFlush(Unpooled.copiedBuffer("999".getBytes())).addListener(ChannelFutureListener.CLOSE);
            System.out.println("发送完成");

        }finally {
//            buffer.release();
//            ReferenceCountUtil.release(req);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read complete");
    }

}
