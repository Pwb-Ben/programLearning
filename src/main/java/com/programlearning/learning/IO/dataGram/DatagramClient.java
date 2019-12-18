package com.programlearning.learning.IO.dataGram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class DatagramClient {

    public static void main(String[] args) {
        DatagramChannel dc = null;
        try {
            dc = DatagramChannel.open();

            dc.configureBlocking(false);

            ByteBuffer buf = ByteBuffer.allocate(1024);

            Scanner scan = new Scanner(System.in);

            while (scan.hasNext()){
                String str = scan.next();
                buf.put(str.getBytes());
                buf.flip();
                dc.send(buf, new InetSocketAddress("127.0.0.1", 12306));
                buf.clear();
            }

            dc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
