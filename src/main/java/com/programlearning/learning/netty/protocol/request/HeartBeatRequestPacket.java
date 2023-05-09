package com.programlearning.learning.netty.protocol.request;

import com.programlearning.learning.netty.protocol.Packet;
import com.programlearning.learning.netty.protocol.command.Command;

public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}