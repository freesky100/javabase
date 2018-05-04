package com.java.base.socket.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

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
public class FileChannelTest {

    /**
     *
     *
     * 读文件
     */
    private void read(String fileName){

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName,"rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(100);
            //Scattering Reads
            /*
            Scattering Reads是指数据从一个channel读取到多个buffer中
            ByteBuffer header = ByteBuffer.allocate(128);
            ByteBuffer body   = ByteBuffer.allocate(1024);
            ByteBuffer[] bufferArray = { header, body };
            channel.read(bufferArray);
            Scattering Reads在移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息(译者注：消息大小不固定)。
            换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。

            Gathering Writes
            channel.write(bufferArray);
            Gathering Writes是指数据从多个buffer写入到同一个channel。如下图描述：
            buffers数组是write()方法的入参，write()方法会按照buffer在数组中的顺序，将数据写入到channel，
            注意只有position和limit之间的数据才会被写入。因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。
            因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。
             */

            int len = fileChannel.read(buffer);
            while(len!=-1){
                System.out.println("Read:"+buffer);
                System.out.println("字体："+new String(buffer.array(),"GBk"));
//                buffer.flip();
//                while(buffer.hasRemaining()){
//                    System.out.println("单字："+(char)buffer.get());
//                }
                buffer.clear();
                len = fileChannel.read(buffer);
            }
            fileChannel.close();;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件复制
     * @param from
     * @param to
     */
    private  void tranferAndfrom(String from ,String to){
        try {
            RandomAccessFile fromFile = new RandomAccessFile(from,"rw");
            RandomAccessFile toFile = new RandomAccessFile(to,"rw");
//            fromFile.getChannel().transferTo(0, fromFile.length(),toFile.getChannel());
            toFile.getChannel().transferFrom( fromFile.getChannel(),0,fromFile.length());
            System.out.println(toFile.getChannel().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String content,String dest){
        try {
            FileOutputStream out = new FileOutputStream(dest);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int size = content.length();
            System.out.println("String size;"+size);
            int lun = size%1024==0?size/1024:size/1024+1;
            for(int i=0;i<lun;i++){
                String write;
                if(i==(lun-1)){
                    write = content.substring(i*1024);
                }else {
                    write = content.substring(i * 1024, (i + 1) * 1024);
                }
                System.out.println(i+"----"+write);
                buffer.put(write.getBytes("GBK"));
                buffer.flip();
                while(buffer.hasRemaining()) {
                    out.getChannel().write(buffer);
                }
                buffer.clear();
            }

        out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
//        new FileChannelTest().read("D:\\test.txt");
//        new FileChannelTest().tranferAndfrom("D:\\test.txt","D:\\test2.txt");
        String content = "在使用FileChannel之前，必须先打开它。但是，我们无法直接打开一个FileChannel，需要通过使用一个InputStream、";
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < 50; i++) {
            for(int j=0;j<i;j++){
                sb.append("*");
            }
            sb.append("\n");
        }
        for (int i = 1; i < 50; i++) {
            for(int j=50-i;j>0;j--){
                sb.append("*");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        new FileChannelTest().writeFile(sb.toString(),"D:\\test3.txt");
    }

}
