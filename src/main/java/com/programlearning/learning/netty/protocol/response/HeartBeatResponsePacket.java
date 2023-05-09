package com.programlearning.learning.netty.protocol.response;

import com.programlearning.learning.netty.protocol.Packet;
import com.programlearning.learning.netty.protocol.command.Command;

public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}