package com.programlearning.learning.war3connection;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws Exception {
//        testUdp();
        testBroadcast();
    }

    private static void testUdp() throws UnknownHostException {
        int port = 6112; // 监听端口


        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress.getHostAddress());
        try (DatagramSocket socket = new DatagramSocket(new InetSocketAddress(inetAddress, 6112))) {
            System.out.println("UDP 监听器已启动，监听端口：" + port);

            byte[] buffer = new byte[1024];

            while (true) {
                // 接收数据
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // 阻塞等待数据

                // 打印接收到的内容和发送方地址
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.printf("收到来自 %s:%d 的消息: %s%n",
                        packet.getAddress().getHostAddress(),
                        packet.getPort(),
                        message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testBroadcast() {
        int port = 6112;

        try (DatagramSocket socket = new DatagramSocket(new InetSocketAddress("127.0.2.1", 6112))) {
            socket.setBroadcast(true); // 允许广播

            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");

            String text = "我的 自定 义房间 A";
            byte[] customText = text.getBytes(StandardCharsets.UTF_8);

            // 1. 创建30字节数组，默认填充0x20（空格）
            byte[] paddedBytes = new byte[31];
            Arrays.fill(paddedBytes, (byte) 0x20); // 全部初始化为0x20

            // 2. 拷贝原数据（确保不越界）
            int copyLength = Math.min(customText.length, 31);
            System.arraycopy(customText, 0, paddedBytes, 0, copyLength);

            // 3. 转换为十六进制字符串
            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : paddedBytes) {
                hexBuilder.append(String.format("%02x ", b));
            }


            var data = hexStringToByteArray("f7 30 93 00 50 58\n" +
                    "33 57 1b 00 00 00 01 00 00 00 6e 8d b4 00 " + hexBuilder.toString() + " 00 00 01\n" +
                    "03 49 07 01 01 a1 01 b9 49 01 13 8f e7 6f 4d 8b\n" +
                    "61 71 73 5d 29 33 29 ad 43 6f 6f 75 79 43 61 bb\n" +
                    "79 2f 77 33 6d 01 61 15 63 63 65 a3 8f 01 01 15\n" +
                    "77 e3 3b 25 fd 81 5d 73 43 77 39 53 93 41 d1 39\n" +
                    "73 ad cb 3f 77 21 00 02 00 00 00 09 00 00 00 01\n" +
                    "00 00 00 02 00 00 00 20 00 00 00 e0 17         \n");

            DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, port);
            socket.send(packet);

            System.out.println("广播数据已发送到端口 " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testBind() {
        int port = 6112;

        try {
            // 选择你想绑定的地址（可选）
            InetSocketAddress address = new InetSocketAddress("0.0.0.0", port);

            // 创建 DatagramChannel
            DatagramChannel channel = SelectorProvider.provider().openDatagramChannel();

            // 设置 SO_REUSEADDR
            channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

            // 尝试绑定
            channel.bind(address);

            System.out.println("绑定成功: " + address);

            // 接收数据（可选）
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (true) {
                SocketAddress sender = channel.receive(buf);
                buf.flip();
                byte[] data = new byte[buf.remaining()];
                buf.get(data);
                System.out.printf("收到来自 %s 的数据: %s%n", sender, new String(data));
                buf.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static byte[] hexStringToByteArray(String s) {
        s = s.replaceAll("\\s+", "");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 将每两个字符转换成一个字节
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
