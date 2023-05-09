package com.programlearning.learning.netty.server.handler;

import com.programlearning.learning.netty.protocol.request.MessageRequestPacket;
import com.programlearning.learning.netty.protocol.response.MessageResponsePacket;
import com.programlearning.learning.netty.session.Session;
import com.programlearning.learning.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        long begin = System.currentTimeMillis();
        System.out.println("begin:"+begin);

        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3.拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4.将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket).addListener(future -> {
                if (future.isDone()) {
                	long end = System.currentTimeMillis();
                	System.out.println("end:"+end);
                	System.out.println("将消息发送给"+session.getUserName()+"耗时："+(end-begin));
                }
            });
        } else {
            System.err.println("[" + session.getUserId() + "] 不在线，发送失败!");
        }
    }
}