package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.Ping;
import com.potxxx.firstim.message.Pong;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PingHandler extends SimpleChannelInboundHandler<Ping> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Ping ping) throws Exception {
        channelHandlerContext.writeAndFlush(new Pong());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.READER_IDLE){
                ctx.close();
                log.info("客户端{}超时主动断开连接",ctx.channel().remoteAddress().toString());
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
