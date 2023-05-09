package com.programlearning.learning.netty.protocol.request;

import com.programlearning.learning.netty.protocol.Packet;
import com.programlearning.learning.netty.protocol.command.Command;

import java.util.List;

public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}