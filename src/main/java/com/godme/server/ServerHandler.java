package com.godme.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        if (!"127.0.0.1".equals(address.getHostName())){
            ctx.close();
            return;
        }
        if (!"godme-godme".equals(msg)) {
            ctx.writeAndFlush("你没有权限访问该服务:username-password");
            return;
        }
        System.out.println(address.getHostName() + "发起请求");
        ctx.writeAndFlush(LocalDateTime.now().toString());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
