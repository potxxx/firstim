package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.message.PullResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class PullResponseHandler extends SimpleChannelInboundHandler<PullResponse> {

    AtomicLong maxAckMsgId;
    public PullResponseHandler(AtomicLong maxAckMsgId){
        this.maxAckMsgId = maxAckMsgId;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PullResponse pullResponse) throws Exception {
        if(pullResponse.getMsgs() == null||pullResponse.getMsgs().size() == 0) return;
        long maxid = 0;
        for(Msg m:pullResponse.getMsgs()){
            maxid = Math.max(maxid,m.getId());
            log.info(" msg from {} -- {}",m.getMsgFrom(),m.getMsgContent());
        }
        maxAckMsgId.set(maxid);
    }
}
