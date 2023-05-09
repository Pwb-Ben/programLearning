package com.programlearning.learning.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class weTalkNIOServer {

    //private static final String EXIT_MARK = "exit";

    private static final int BUFFER_SIZE = 128;

    private int port;

    weTalkNIOServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 1.获取通道
        ServerSocketChannel server = ServerSocketChannel.open();
        // 2.切换成非阻塞模式
        server.configureBlocking(false);
        // 3. 绑定连接
        server.bind(new InetSocketAddress(port));
        // 4. 获取选择器
        Selector selector = Selector.open();
        // 4.1将通道注册到选择器上，指定接收“监听通道”事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端已启动，正在监听 " + port + " 端口......");

        // 5. 轮询获取选择器上已“就绪”的事件--->只要select()>0，说明已就绪
        while (selector.select() > 0) {
            // 6. 获取当前选择器所有注册的“选择键”(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            // 7. 获取已“就绪”的事件，(不同的事件做不同的事)
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 接收事件就绪
                if (selectionKey.isAcceptable()) {
                    // 8. 获取客户端的链接
                    SocketChannel channel = server.accept();
                    System.out.println("接受来自" + channel.getRemoteAddress().toString().replace("/", "") + " 请求");
                    // 8.1 切换成非阻塞状态
                    channel.configureBlocking(false);
                    // 8.2 注册到选择器上-->拿到客户端的连接为了读取通道的数据(监听读就绪事件)
                    channel.register(selector, SelectionKey.OP_READ);

                    new Thread(() -> {
                        Scanner sc = new Scanner(System.in);
                        System.out.println("请输入：");
                        while(sc.hasNextLine()) {
                            String msg = sc.nextLine();
                            try {
                                System.out.println("发送的msg："+msg);
                                sendMsg(channel, msg);
                            }catch(IOException e) {
                                System.err.println("IO异常："+e.getMessage());
                                break;
                            }
                            System.out.println("请输入：");
                        }
                        sc.close();
                    }).start();
                } else if (selectionKey.isReadable()) { // 读事件就绪
                    // 9. 获取当前选择器读就绪状态的通道
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    String msg = receiveMsg(channel);
                    System.out.println("\n客户端：");
                    System.out.println(msg + "\n");
                }
                // 10. 取消选择键(已经处理过的事件，就应该取消掉了)
                iterator.remove();
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

    public static void main(String[] args) throws IOException{
        new weTalkNIOServer(8080).start();
    }
}
