package com.programlearning.learning.war3connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Client {

    private static final String serverIp = "45.207.49.157";
    private static final int serverPort = 8888;

    //虚拟线程池
    private static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private static final ConcurrentHashMap<String, War3Server> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        var connectionServer = new ConnectionServer();
        var broadcast = new Broadcast();
        broadcast.connectionServer = connectionServer;

        executor.submit(connectionServer);
        Future<?> submit = executor.submit(broadcast);
        submit.get();
    }

    static class ConnectionServer implements Runnable {

        private String ip;
        private AsynchronousSocketChannel _asc;

        @Override
        public void run() {
            try (var asc = AsynchronousSocketChannel.open()) {
                this._asc = asc;
                // 连接到服务器
                asc.connect(new InetSocketAddress(serverIp, serverPort)).get();
                // 告诉是客户端连接
                var buffer = ByteBuffer.allocate(4).putInt(100).flip();
                asc.write(buffer).get();

                buffer = ByteBuffer.allocate(1024);
                var isFirst = true;
                while (!Thread.interrupted()) {

                    int bytesRead = asc.read(buffer).get();
                    if (bytesRead > 0) {
                        buffer.flip();

                        if (isFirst) {
                            isFirst = false;

                            int len = buffer.getInt();
                            byte[] data = new byte[len];
                            buffer.get(data);

                            String ips = new String(data);
                            System.out.println("收到服务器IP列表: " + ips);
                            String[] split = ips.split("#");
                            ip = split[0];

                            for (int i = 1; i < split.length; i++) {
                                String war3Ip = split[i];
                                War3Server war3Server = new War3Server(war3Ip, this);
                                connectionMap.put(war3Ip, war3Server);
                                executor.submit(war3Server);
                            }
                        } else {
                            int code = buffer.getInt();
                            if (code == 66) {
                                //连接服务器，提供魔兽连接
                                executor.submit(() -> {

                                    //本地魔兽
                                    try (
                                            var localAsc = AsynchronousSocketChannel.open();
                                            var dstAsc = AsynchronousSocketChannel.open();
                                    ) {
                                        //连接本地魔兽
                                        localAsc.connect(new InetSocketAddress("localhost", 6112)).get();
                                        //连接服务器
                                        dstAsc.connect(new InetSocketAddress(serverIp, serverPort)).get();


                                        // 发送来源IP地址
                                        int length = ip.length();
                                        var buf = ByteBuffer.allocate(4 + 4 + length);
                                        buf.putInt(300);
                                        buf.putInt(length);
                                        buf.put(ip.getBytes());
                                        buf.flip();
                                        dstAsc.write(buf).get();


                                        //开始交换数据
                                        Future<?> taskFuture1 = executor.submit(() -> {
                                            transferData(localAsc, dstAsc);
                                        });

                                        Future<?> taskFuture2 = executor.submit(() -> {
                                            transferData(dstAsc, localAsc);
                                        });

                                        taskFuture1.get();
                                        taskFuture2.get();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                });

                            } else if (code == 88) {
                                // 发广播
                                int len = buffer.getInt();
                                var data = new byte[len];
                                buffer.get(data);
                                var ip = new String(data);
                                //广播内容
                                data = new byte[buffer.remaining()];
                                buffer.get(data);

                                War3Server war3Server = connectionMap.get(ip);
                                if (war3Server != null) {
                                    war3Server.udpSend(data);
                                }

                            } else if (code == 99) {
                                int len = buffer.getInt();
                                var data = new byte[len];
                                buffer.get(data);
                                String ips = new String(data);
                                String[] split = ips.split("#");

                                for (String ip : split) {
                                    if (!connectionMap.containsKey(ip)) {
                                        var war3Server = new War3Server(ip, this);
                                        connectionMap.put(ip, war3Server);
                                        executor.submit(war3Server);
                                    } else {
                                        System.out.println("连接已存在: " + ip);
                                    }
                                }

                                //删除不存在的ip
                                for (String s : Collections.list(connectionMap.keys())) {
                                    if (!Arrays.asList(split).contains(s)) {
                                        connectionMap.get(s).close();
                                        connectionMap.remove(s);
                                        System.out.println("删除不存在的连接: " + s);
                                    }
                                }
                            }
                        }
                        buffer.clear();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void writeServer(String ip, byte[] data, int len) {
            if (_asc != null && _asc.isOpen()) {
                try {
                    int length = ip.length();
                    var buf = ByteBuffer.allocate(4 + length + len);
                    buf.putInt(length);
                    buf.put(ip.getBytes());
                    buf.put(data, 0, len);
                    buf.flip();

                    _asc.write(buf).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("连接已关闭，无法发送数据");
            }
        }
    }

    static class Broadcast implements Runnable {

        private ConnectionServer connectionServer;

        @Override
        public void run() {
            int port = 6112; // 监听端口

            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                try (DatagramSocket socket = new DatagramSocket(new InetSocketAddress(inetAddress, port))) {
                    System.out.println("UDP 广播监听器已启动，监听IP: " + inetAddress.getHostAddress() + " 端口: " + port);
                    byte[] buffer = new byte[1024];

                    while (!Thread.interrupted()) {
                        // 接收数据
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet); // 阻塞等待数据
                        //只处理广播
                        if (packet.getAddress().getHostAddress().equals(inetAddress.getHostAddress())) {
                            String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            System.out.printf("[%s] 收到来自 %s:%d 的消息: %s%n",
                                    format,
                                    packet.getAddress().getHostAddress(),
                                    packet.getPort(),
                                    new String(packet.getData(), 0, packet.getLength()));
                            for (War3Server value : connectionMap.values()) {
                                connectionServer.writeServer(value.ip, packet.getData(), packet.getLength());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class War3Server implements Runnable {

        String ip;

        int port = 6112;

        Future<?> tcpFuture;

        Future<?> udpFuture;

        ConnectionServer connectionServer;

        UdpListener udpListener;

        public War3Server(String ip, ConnectionServer connectionServer) {
            this.ip = ip;
            this.connectionServer = connectionServer;
        }

        @Override
        public void run() {
            try {
                // 启动TCP监听器
                var tcpListener = new TcpListener(port, ip);
                tcpFuture = executor.submit(tcpListener);

                // 启动UDP监听器
                udpListener = new UdpListener(port, ip, connectionServer);
                udpFuture = executor.submit(udpListener);

                System.out.println("War3Server已启动，监听IP: " + ip + " 端口: " + port);

                // 等待TCP监听器完成
                tcpFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void close() {
            try {
                if (tcpFuture != null) {
                    tcpFuture.cancel(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void udpSend(byte[] data) {
            if (udpListener != null) {
                udpListener.send(data);
            } else {
                System.out.println("UDP监听器未启动，无法发送数据");
            }
        }

        static class TcpListener implements Runnable {

            int port;
            String ip;

            public TcpListener(int port, String ip) {
                this.port = port;
                this.ip = ip;
            }

            @Override
            public void run() {
                try (var assc = AsynchronousServerSocketChannel.open()) {
                    assc.bind(new InetSocketAddress(ip, port));
                    System.out.println("TCP 监听器已启动，监听IP: " + ip + " 端口: " + port);
                    while (!Thread.interrupted()) {
                        var asc = assc.accept().get();

                        // 每个新连接交给线程池异步处理
                        executor.submit(() -> {
                            //连接服务器
                            try (var dstAsc = AsynchronousSocketChannel.open()) {

                                dstAsc.connect(new InetSocketAddress(serverIp, serverPort)).get();

                                // 发送目标IP地址
                                int length = ip.length();

                                var buffer = ByteBuffer.allocate(4 + 4 + length);
                                buffer.putInt(200);
                                buffer.putInt(length);
                                buffer.put(ip.getBytes());
                                buffer.flip();
                                dstAsc.write(buffer).get();


                                //开始交换数据
                                Future<?> taskFuture1 = executor.submit(() -> {
                                    transferData(dstAsc, asc);
                                });

                                Future<?> taskFuture2 = executor.submit(() -> {
                                    transferData(asc, dstAsc);
                                });

                                taskFuture1.get();
                                taskFuture2.get();

                            } catch (Exception e) {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        static class UdpListener implements Runnable {

            int port;
            String ip;

            ConnectionServer connectionServer;

            DatagramSocket _socket;

            public UdpListener(int port, String ip, ConnectionServer connectionServer) {
                this.port = port;
                this.ip = ip;
                this.connectionServer = connectionServer;
            }

            @Override
            public void run() {
                try (DatagramSocket socket = new DatagramSocket(new InetSocketAddress(ip, port))) {
                    socket.setBroadcast(true); // 允许广播
                    _socket = socket;
                    byte[] receiveData = new byte[1024];
                    System.out.println("UDP 监听器已启动，监听IP: " + ip + " 端口: " + port);

                    while (!Thread.interrupted()) {
                        try {
                            DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                            socket.receive(packet);

                            if (!packet.getAddress().getHostAddress().equals(ip)) {
                                System.out.println("收到数据来自: " + packet.getAddress().getHostAddress() + " 端口: " + packet.getPort() + " 数据长度: " + packet.getLength());
                                connectionServer.writeServer(ip, packet.getData(), packet.getLength());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void send(byte[] data) {
                try {
                    if (_socket != null && !_socket.isClosed()) {
                        InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
                        if (data.length > 100) {
                            //修改房间名
                            String text = "点击我加入房间";
                            byte[] customText = text.getBytes(StandardCharsets.UTF_8);

                            // 1. 创建30字节数组，默认填充0x20（空格）
                            byte[] paddedBytes = new byte[31];
                            Arrays.fill(paddedBytes, (byte) 0x20); // 全部初始化为0x20

                            // 2. 拷贝原数据（确保不越界）
                            int copyLength = Math.min(customText.length, 31);
                            System.arraycopy(customText, 0, paddedBytes, 0, copyLength);

                            for (int i = 20; i < paddedBytes.length + 20; i++) {
                                data[i] = paddedBytes[i - 20];
                            }
                        }
                        System.out.println("data:" + data.length + "发送数据到广播地址: " + broadcastAddress.getHostAddress() + ":" + port);
                        DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, port);
                        _socket.send(packet);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

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
