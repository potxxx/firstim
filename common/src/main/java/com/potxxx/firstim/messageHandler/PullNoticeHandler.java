package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.PullNotice;
import com.potxxx.firstim.message.PullRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class PullNoticeHandler extends SimpleChannelInboundHandler<PullNotice> {
    AtomicBoolean canPullMsg;
    public PullNoticeHandler(AtomicBoolean canPullMsg){
        this.canPullMsg = canPullMsg;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PullNotice pullNotice) throws Exception {
        log.info("pullNotice {}",pullNotice.getUseId());
        canPullMsg.set(true);
    }
}
