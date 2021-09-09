package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class C2CSendRequestHandler extends SimpleChannelInboundHandler<C2CSendRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, C2CSendRequest c2CSendRequest) throws Exception {
        log.info("get {}",c2CSendRequest.getContent());
        channelHandlerContext.writeAndFlush(new C2CSendResponse("11","12","12"));
    }
}
