package com.programlearning.learning.NIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SocketIO {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            new Thread(()->{
                try {
                    Socket socket = serverSocket.accept();

                    new Thread(()->{
                        int len;
                        byte[] data = new byte[1024];
                        try {
                            InputStream inputStream = socket.getInputStream();
                            // (3) 按字节流方式读取数据
                            while (true) {
                                if (!((len = inputStream.read(data)) != -1)) break;
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            try {
                Socket socket = new Socket("127.0.0.1",8080);

                while(true){
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write((new Date() + "Hello!").getBytes());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
