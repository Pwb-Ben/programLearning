package com.programlearning.learning.netty.protocol.request;

import com.programlearning.learning.netty.protocol.Packet;
import com.programlearning.learning.netty.protocol.command.Command;

public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}