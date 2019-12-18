package com.programlearning.learning.IO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

public class SocketAIO {

    private AsynchronousServerSocketChannel serverSocketChannel;

    public static void main(String[] args) {
        try {
            SocketAIO socketAIO = new SocketAIO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SocketAIO() throws Exception {
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 12306));
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                System.out.println("服务器接受连接");
                try {
                    sendDataToClient(socketChannel);
                    serverSocketChannel.accept(null, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable t, Object attachment) {
                t.printStackTrace();
            }
        });

        // AIO非阻塞，需要在主进程阻塞，否则进程退出导致套接字关闭，服务器接受不到任何连接。
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendDataToClient(AsynchronousSocketChannel socketChannel) throws Exception {
        System.out.println("服务器与" + socketChannel.getRemoteAddress() + "建立连接");

        ByteBuffer buffer = ByteBuffer.wrap("zhangphil".getBytes());
        Future<Integer> write = socketChannel.write(buffer);

        while (!write.isDone()) {
            Thread.sleep(10);
        }

        System.out.println("服务器发送数据完毕.");
        socketChannel.close();
    }
}
