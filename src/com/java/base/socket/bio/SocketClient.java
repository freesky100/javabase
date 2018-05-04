package com.java.base.socket.bio;

import java.io.*;
import java.net.Socket;

/**
 * Created by yw on 2018/5/3.
 */
public class SocketClient {

    protected  static String ip = "localhost";
    protected static int port = 6666;
    public static void main(String[] args) {
        Socket socket=null;
        try {
            socket = new Socket(ip,port);
           boolean flag =  socket.isConnected();
            System.out.println("客户端发起请求,"+flag);
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("你好，服务器。。。");
            bufferedWriter.flush();
            socket.shutdownOutput();
            System.out.println("发送完成");
            String res=null;
            while((res=bufferedReader.readLine())!=null){
                System.out.println("服务器响应："+res);
            }
//            socket.shutdownInput();
//            bufferedReader.close();
//            bufferedWriter.close();
            if(socket!=null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{

        }



    }
}
