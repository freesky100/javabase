package com.java.base.socket.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

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
public class MappedByteBufferTest {

    private MappedByteBuffer mappedByteBuffer;

    public void writeFile(String fileName){

        try {
            RandomAccessFile accessFile = new RandomAccessFile(fileName,"rw");
            FileChannel fileChannel = accessFile.getChannel();
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,1024);

            byte[] readContent = new byte[2];
            for(int i=0;i<10;i++) {
//               mappedByteBuffer.get(readContent);
//                System.out.println(new String(readContent,"GBK"));
                byte temp = mappedByteBuffer.get();
                //处理中文问题，中文2个字节
                if(temp<255 && temp >0){
                    System.out.println((char)(temp));
                }else{
                    readContent[0]=temp;
                   readContent[1]= mappedByteBuffer.get();
                    System.out.println(new String(readContent,"GBK"));
                    i++;
                }
            }
            mappedByteBuffer.flip();
            mappedByteBuffer.put("文".getBytes());

            fileChannel.close();
            accessFile.close();
        } catch (FileNotFoundException e) {
                    e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MappedByteBufferTest().writeFile("D://test.txt");
    }
}
