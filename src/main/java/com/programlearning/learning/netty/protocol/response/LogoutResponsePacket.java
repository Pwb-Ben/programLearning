package com.programlearning.learning.netty.protocol.response;

import com.programlearning.learning.netty.protocol.Packet;
import com.programlearning.learning.netty.protocol.command.Command;

public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}