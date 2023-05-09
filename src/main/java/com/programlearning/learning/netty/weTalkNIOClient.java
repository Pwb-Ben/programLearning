package com.programlearning.learning.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class weTalkNIOClient {

    //private static final String EXIT_MARK = "exit";

    private static final int BUFFER_SIZE = 128;

    private String hostname;

    private int port;

    weTalkNIOClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void start() throws IOException{
        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(hostname, port));
        // 1.1切换成非阻塞模式
        socketChannel.configureBlocking(false);
        // 1.2获取选择器
        Selector selector = Selector.open();
        // 1.3将通道注册到选择器中，获取服务端返回的数据
        socketChannel.register(selector, SelectionKey.OP_READ);

        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入：");
            while(sc.hasNextLine()) {
                String msg = sc.nextLine();
                try {
                    System.out.println("发送的msg："+msg);
                    sendMsg(socketChannel, msg);
                }catch(IOException e) {
                    System.err.println("IO异常："+e.getMessage());
                    break;
                }
                System.out.println("请输入：");
            }
            sc.close();
        }).start();

        // 5. 轮训地获取选择器上已“就绪”的事件--->只要select()>0，说明已就绪
        while (selector.select() > 0) {
            // 6. 获取当前选择器所有注册的“选择键”(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            // 7. 获取已“就绪”的事件，(不同的事件做不同的事)
            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();

                // 8. 读事件就绪
                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    String msg = receiveMsg(channel);
                    System.out.println("\n服务端：");
                    System.out.println(msg + "\n");
                }
            }
        }
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
        new weTalkNIOClient("localhost", 8080).start();
    }
}
