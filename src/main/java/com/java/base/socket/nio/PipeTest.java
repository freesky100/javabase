package com.java.base.socket.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

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
public class PipeTest {

    private static int count =0;
    private   Pipe pipe;
    private Pipe.SinkChannel sinkChannel;
    private Pipe.SourceChannel sourceChannel;


    public PipeTest() throws IOException {
       pipe = Pipe.open();
    }
    private void write() {
        try {
            sinkChannel = pipe.sink();
            while (true) {
                String msg = Runtime.getRuntime().availableProcessors() + "--写入时间:" + count + "----" + System.currentTimeMillis();
                ByteBuffer buffer = ByteBuffer.allocate(48);
                buffer.put(msg.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    sinkChannel.write(buffer);
                }
                buffer.clear();
                System.out.println("sink write:" + msg);
                if (count > 10) {
                    break;
                }
                count++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sinkChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void read(){

        sourceChannel = pipe.source();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        try {
            int len = sourceChannel.read(buffer);
//            System.out.println(len);
            while(len>0){
               buffer.flip();
               byte[] bytes = new byte[len];
               buffer.get(bytes,0,len);
               System.out.println("Soure read："+new String(bytes));

//                buffer.flip();
                buffer.clear();
                len = sourceChannel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sourceChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final PipeTest pipeTest = new PipeTest();
        new Thread(()->{
            pipeTest.write();
        },"t1").start();
        new Thread(()->{
            pipeTest.read();
        },"t2").start();
//        System.out.println("----");
//        ByteBuffer buffer = ByteBuffer.allocate(48);
//        while(true){
//            System.out.println("00000");
//            System.out.println( pipeTest.pipe.source().read(buffer));;
//            Thread.sleep(1000);
//            buffer.clear();
//
//        }


    }


}
