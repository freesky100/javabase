package com.java.base.socket.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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
public class BufferTest {

    private ByteBuffer byteBuffer = ByteBuffer.allocate(2<<3);

    public void buffer(){

        //1:
        System.out.println("初始化："+byteBuffer);
        for (int i = 0; i <10; i++) {
             byteBuffer.put((byte)i);
        }
        System.out.println("添加数据后："+byteBuffer);

        for(int i=0;i<5;i++){
            System.out.println(byteBuffer.get());
        }
        System.out.println("获取数据后:"+byteBuffer);
        byteBuffer.flip();
        System.out.println("flip后数据:"+byteBuffer);
        byte[] res = new byte[byteBuffer.remaining()];
        System.out.println(byteBuffer.get(res));
        for (int i = 0; i < res.length; i++) {
            System.out.println(i+"i:"+res[i]);

        }

    }



    public static void main(String[] args) {

        new BufferTest().buffer();


        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println(buffer.position());
//        向Buffer中写数据
//        写数据到Buffer有两种方式：
//        从Channel写到Buffer。
//        通过Buffer的put()方法写到Buffer里。
        buffer.put(21);
        buffer.put(2);
        buffer.put(2);
        System.out.println(buffer.position());
        //read，此时可以看一下pos
        System.out.println(buffer.get());
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);
        System.out.println(buffer.get());
        System.out.println(buffer.position());
        System.out.println("---------------------");
        //rewind  Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。
        // limit保持不变，仍然表示能从Buffer中读取多少个元
        buffer.rewind();
        System.out.println(buffer);
        System.out.println("---------------------");
        //clear compact
        //clear 只手pos指向0，单数据并未清除
        buffer.clear();
        System.out.println(buffer);
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(buffer.get());
        }
        buffer.rewind();
        System.out.println("---------------------"+buffer);
        //添加新数据
        buffer.put(55);
        buffer.put(56);
        buffer.put(57);
        //compact 有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
        System.out.println("compact:"+buffer);
        buffer.flip();
        System.out.println(buffer.get());
        buffer.compact();
        buffer.put(31);
        System.out.println(buffer);
        buffer.flip();
        while(buffer.remaining()>0){
            System.out.println(buffer.get());
        }
    }
}
