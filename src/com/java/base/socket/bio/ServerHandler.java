package com.java.base.socket.bio;

import java.io.*;
import java.net.Socket;

/**
 * Created by yw on 2018/5/3.
 */
public class ServerHandler implements  Runnable{

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[100];
            int len =0;
            String content = null;
//            StringBuffer sb = new StringBuffer();

            while((content = bufferedReader.readLine())!=null){
                System.out.println("收到客户端消息:"+content);
//                sb.append(new String(buffer,0,len));
            }
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.write("你好,"+socket.getInetAddress().getHostAddress());
            System.out.println("返回响应信息：你好,"+socket.getInetAddress().getHostAddress());
            printWriter.flush();
            bufferedReader.close();
             printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
