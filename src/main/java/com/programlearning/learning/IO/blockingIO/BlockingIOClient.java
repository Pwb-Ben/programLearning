package com.programlearning.learning.IO.blockingIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BlockingIOClient {

    public static void main(String[] args) throws IOException {

        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 12306));

        // 2. 发送一张图片给服务端吧
        FileChannel fileChannel = FileChannel.open(Paths.get("X:\\Users\\ozc\\Desktop\\新建文件夹\\1.png"), StandardOpenOption.READ);

        // 3.要使用NIO，有了Channel，就必然要有Buffer，Buffer是与数据打交道的呢
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 4.读取本地文件(图片)，发送到服务器
        while (fileChannel.read(buffer) != -1) {

            // 在读之前都要切换成读模式
            buffer.flip();

            socketChannel.write(buffer);

            // 读完切换成写模式，能让管道继续读取文件的数据
            buffer.clear();
        }

        // 在这里显式告诉服务端已经写完
        socketChannel.shutdownOutput();

        // 知道服务端要返回响应数据给客户端，客户端在这里接收
        int len = 0;
        while ((len = socketChannel.read(buffer)) != -1){
            //切换读
            buffer.flip();

            System.out.println(new String(buffer.array(),0,len));

            //切换写
            buffer.clear();
        }
        /**
         * 如果仅仅是上面的代码是不行的！这个程序会阻塞起来！
         *
         * 因为服务端不知道客户端还有没有数据要发过来(与刚开始不一样，客户端发完数据就将流关闭了，服务端可以知道客户端没数据发过来了)，
         * 导致服务端一直在读取客户端发过来的数据。进而导致了阻塞！
         *
         * 于是客户端在写完数据给服务端时，显式告诉服务端已经发完数据了！
         */

        // 5. 关闭流
        fileChannel.close();
        socketChannel.close();
    }
}
