package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class C2CSendResponseHandler extends SimpleChannelInboundHandler<C2CSendResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, C2CSendResponse c2CSendResponse) throws Exception {
        log.info("from server ackID:{}",c2CSendResponse.getAckId());
    }
}
