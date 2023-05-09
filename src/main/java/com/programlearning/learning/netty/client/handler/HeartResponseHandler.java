package com.programlearning.learning.netty.client.handler;

import com.programlearning.learning.netty.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {

    public static final HeartResponseHandler INSTANCE = new HeartResponseHandler();

    private HeartResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket responsePacket) {}
}