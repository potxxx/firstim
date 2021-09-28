package com.potxxx.firstim.messageHandler;

import com.google.common.util.concurrent.RateLimiter;
import com.potxxx.firstim.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RateLimitHandler extends SimpleChannelInboundHandler<Message> {

    static RateLimiter rateLimiter = RateLimiter.create(2000);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        rateLimiter.acquire();
        log.info("RateLimiter pass...");
        channelHandlerContext.fireChannelRead(message);
    }
}
