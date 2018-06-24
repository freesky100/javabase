package com.java.base.socket.bio;

import java.io.*;
import java.net.Socket;

/**
 * Created by yw on 2018/5/3.
 */
public class Client {

    public static void main(String[] args) {
        Socket socket=null;
        try {

            System.out.println("客户端发起请求");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader socketReader = null;
            PrintWriter bufferedWriter = null;
            while(true){
                socket = new Socket("localhost",6669);
                socketReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

                String send = bufferedReader.readLine();
                bufferedWriter.write(send);
                System.out.println("发送完成"+send);
                bufferedWriter.flush();
                socket.shutdownOutput();


                String res=socketReader.readLine();
               if(send.equalsIgnoreCase("bye")){
                   break;
               }
                System.out.println("获取服务器响应："+res);
            }

//            socket.shutdownInput();
            bufferedReader.close();
            bufferedWriter.close();
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
