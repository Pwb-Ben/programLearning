package com.programlearning.learning.war3connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Server {

    // 使用虚拟线程池处理并发任务
    private static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    // 保存所有已连接的客户端
    private static final ConcurrentHashMap<String, Client> connectionMap = new ConcurrentHashMap<>();
    // IP地址池，用于分配给新连接的客户端
    private static final IPPool ipPool = new IPPool();

    public static void main(String[] args) throws Exception {
        // 打开异步服务器Socket通道
        var assc = AsynchronousServerSocketChannel.open();
        assc.bind(new InetSocketAddress("0.0.0.0", 8888));
        System.out.println("服务器已启动，监听端口 8888");
        // 主循环，持续接受客户端连接
        while (!Thread.interrupted()) {
            var asc = assc.accept().get();
            // 每个新连接交给线程池异步处理
            executor.submit(() -> handleClient(asc));
        }
    }

    // 处理单个客户端连接
    private static void handleClient(AsynchronousSocketChannel asc) {
        try {
            var buffer = ByteBuffer.allocate(1024);
            var len = asc.read(buffer).get();

            if (len > 0) {
                buffer.flip();
                int value = buffer.getInt();

                if (value == 100) {//客户端连接
                    handleClientConnect(asc);
                } else if (value == 200) {//魔兽连接
                    handleWar3Connect(buffer, asc);
                } else if (value == 300) {//提供魔兽连接
                    handleProvideWar3(buffer, asc);
                } else {
                    System.out.println("Unknown command: " + value);
                    asc.close();
                }
                buffer.clear();
            } else if (len < 0) {
                asc.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                asc.close();
            } catch (IOException ignored) {
            }
        }
    }

    // 处理客户端连接命令，分配IP并注册到connectionMap
    private static void handleClientConnect(AsynchronousSocketChannel asc) throws Exception {
        String ip = ipPool.acquireIP();

        String otherIp = connectionMap.keySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining("#"));

        String ips = ip + "#" + otherIp;
        System.out.println(ips);

        ByteBuffer buffer = ByteBuffer.allocate(4 + ips.length())
                .putInt(ips.length()) // 长度
                .put(ips.getBytes()) // IP列表
                .flip();

        asc.write(buffer).get();

        var client = new Client(ip, asc);
        connectionMap.put(ip, client);
        executor.submit(client);

        for (Client value : connectionMap.values()) {
            // 更新客户端列表
            executor.submit(value::updateClientList);
        }

    }

    // 处理魔兽客户端连接命令，建立数据转发通道
    private static void handleWar3Connect(ByteBuffer buffer, AsynchronousSocketChannel asc) throws Exception {
        int l = buffer.getInt();
        var data = new byte[l];
        buffer.get(data);
        String dstIp = new String(data);

        Client client = connectionMap.get(dstIp);
        if (client != null) {
            client.issued();
            try {
                // 等待目标客户端提供的Socket通道
                var dstAsc = client.queue.poll(3, TimeUnit.SECONDS);
                // 双向数据转发
                Future<?> taskFuture1 = executor.submit(() -> transferData(dstAsc, asc));
                Future<?> taskFuture2 = executor.submit(() -> transferData(asc, dstAsc));
                taskFuture1.get();
                taskFuture2.get();
            } catch (Exception e) {
                e.printStackTrace();
                asc.close();
            }
        } else {
            System.out.println("Client not found for IP: " + dstIp);
            asc.close();
        }
    }

    // 处理魔兽服务端提供连接命令，将Socket通道放入队列
    private static void handleProvideWar3(ByteBuffer buffer, AsynchronousSocketChannel asc) throws Exception {
        int l = buffer.getInt();
        var data = new byte[l];
        buffer.get(data);
        String srcIp = new String(data);

        Client client = connectionMap.get(srcIp);
        if (client != null) {
            client.queue.put(asc);
        } else {
            System.out.println("Client not found for IP: " + srcIp);
            asc.close();
        }
    }

    // 客户端对象，负责处理自身的消息和广播
    static class Client implements Runnable {
        String ip;
        AsynchronousSocketChannel asc;
        LinkedBlockingQueue<AsynchronousSocketChannel> queue = new LinkedBlockingQueue<>();

        public Client(String ip, AsynchronousSocketChannel asc) {
            this.ip = ip;
            this.asc = asc;
        }

        // 通知客户端有新事件
        public void issued() {
            if (asc != null && asc.isOpen()) {
                try {
                    var buffer = ByteBuffer.allocate(4).putInt(66).flip();
                    asc.write(buffer).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("连接已关闭，无法发送数据");
            }
        }

        // 更新客户端列表 有新的客户端加入
        public synchronized void updateClientList() {
            if (asc != null && asc.isOpen()) {
                try {
                    String result = Collections.list(connectionMap.keys()).stream()
                            .filter(key -> !key.equals(ip))  // 排除自身IP
                            .map(Object::toString)
                            .collect(Collectors.joining("#"));

                    if (!result.isEmpty()) {
                        var buffer = ByteBuffer.allocate(4 + 4 + result.length());
                        buffer.putInt(99);
                        buffer.putInt(result.length());
                        buffer.put(result.getBytes());
                        buffer.flip();
                        asc.write(buffer).get();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("连接已关闭，无法发送数据");
            }
        }


        // 监听客户端消息并进行广播
        @Override
        public void run() {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (!Thread.interrupted()) {
                    int bytesRead = asc.read(buffer).get();
                    if (bytesRead == -1) break;
                    if (bytesRead > 0) {
                        buffer.flip();

                        int len = buffer.getInt();
                        byte[] data = new byte[len];
                        buffer.get(data);
                        String dstIP = new String(data);
                        data = new byte[buffer.remaining()];
                        buffer.get(data);

                        Client client = connectionMap.get(dstIP);
                        if (client != null) {
                            if (client.asc != null && client.asc.isOpen()) {
                                int length = ip.length();
                                var buf = ByteBuffer.allocate(4 + 4 + length + data.length);
                                buf.putInt(88);
                                buf.putInt(length);
                                buf.put(ip.getBytes());
                                buf.put(data);
                                buf.flip();
                                client.asc.write(buf).get();
                            }
                        } else {
                            System.out.println("目标客户端未找到: " + dstIP);
                        }
                        buffer.clear();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    asc.close();
                    // 从连接映射中移除自身
                    connectionMap.remove(ip);
                    // 释放ip
                    ipPool.releaseIP(ip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 双向转发数据
    private static void transferData(AsynchronousSocketChannel srcChannel, AsynchronousSocketChannel dstChannel) {
        int readLen = 0; // 显式初始化
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (readLen >= 0) {
                readLen = srcChannel.read(buffer).get(); // 获取读取的字节数
                if (readLen > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        dstChannel.write(buffer).get(); // 使用非阻塞写操作
                    }
                    buffer.clear(); // 清空缓冲区，准备下次读取
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
