package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.Ping;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        // 触发了读写空闲事件
        if (event.state() == IdleState.ALL_IDLE) {
            log.debug("300s 没有读写数据了，发送一个心跳包");
            ctx.writeAndFlush(new Ping());
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("连接出现异常被关闭{}",cause.toString());
    }
}
