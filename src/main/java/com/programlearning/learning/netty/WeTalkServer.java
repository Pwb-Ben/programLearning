package com.programlearning.learning.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class WeTalkServer {
    private static final String EXIT_MARK = "exit";

    private static final int BUFFER_SIZE = 128;

    private int port;

    WeTalkServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 创建服务端套接字通道，监听端口，并等待客户端连接
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        System.out.println("服务端已启动，正在监听 " + port + " 端口......");
        SocketChannel channel = ssc.accept();
        System.out.println("接受来自" + channel.getRemoteAddress().toString().replace("/", "") + " 请求");

        Scanner sc = new Scanner(System.in);
        while (true) {
            // 等待并接收客户端发送的消息
            String msg = receiveMsg(channel);
            System.out.println("\n客户端：");
            System.out.println(msg + "\n");

            // 输入信息
            System.out.println("请输入：");
            msg = sc.nextLine();
            if (EXIT_MARK.equals(msg)) {
                sendMsg(channel, "bye~");
                break;
            }

            // 回复客户端消息
            sendMsg(channel, msg);
        }

        // 关闭通道
        sc.close();
        channel.close();
        ssc.close();
    }

    public void sendMsg(SocketChannel channel, String msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.put(msg.getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    public String receiveMsg(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        channel.read(buffer);

        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        return new String(bytes);
    }

    public static void main(String[] args) throws IOException {
        new WeTalkServer(8080).start();
    }
}
