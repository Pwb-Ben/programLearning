package com.programlearning.learning.IO.dataGram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class DatagramServer {

    public static void main(String[] args) {
        try {
            DatagramChannel dc = DatagramChannel.open();

            dc.bind(new InetSocketAddress(12306));

            Selector selector = Selector.open();

            dc.register(selector, SelectionKey.OP_READ);

            while (selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()){
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        dc.receive(buf);
                        buf.flip();
                        System.out.println(new String(buf.array(),0,buf.limit()));
                        buf.clear();
                    }
                }

                iterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
