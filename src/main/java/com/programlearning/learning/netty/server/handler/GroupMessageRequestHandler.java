package com.programlearning.learning.netty.server.handler;

import com.programlearning.learning.netty.protocol.request.GroupMessageRequestPacket;
import com.programlearning.learning.netty.protocol.response.GroupMessageResponsePacket;
import com.programlearning.learning.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) {
        // 1.拿到 groupId 构造群聊消息的响应
        String groupId = requestPacket.getToGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        
        if(channelGroup.contains(ctx.channel())) {
        	GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
            responsePacket.setFromGroupId(groupId);
            responsePacket.setMessage(requestPacket.getMessage());
            responsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

            // 2. 拿到群聊对应的 channelGroup，写到每个客户端
            channelGroup.writeAndFlush(responsePacket);
        }else {
        	System.out.println(SessionUtil.getSession(ctx.channel()).getUserName() + "不在群中");
        }
    }
}