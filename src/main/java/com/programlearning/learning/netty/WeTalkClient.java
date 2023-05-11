package com.programlearning.learning.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class WeTalkClient {
    private static final String EXIT_MARK = "exit";

    private static final int BUFFER_SIZE = 128;

    private String hostname;

    private int port;

    WeTalkClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void start() throws IOException {
        // 打开一个套接字通道，并向服务端发起连接
        SocketChannel channel = SocketChannel.open(new InetSocketAddress(hostname, port));

        Scanner sc = new Scanner(System.in);
        while (true) {
            // 输入信息
            System.out.println("请输入：");
            String msg = sc.nextLine();
            if (EXIT_MARK.equals(msg)) {
                sendMsg(channel, "bye~");
                break;
            }

            // 向服务端发送消息
            sendMsg(channel, msg);

            // 接受服务端返回的消息
            msg = receiveMsg(channel);
            System.out.println("\n服务端：");
            System.out.println(msg + "\n");
        }

        // 关闭通道
        sc.close();
        channel.close();
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
        new WeTalkClient("localhost", 8080).start();
    }
}
