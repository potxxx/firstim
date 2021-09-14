package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.PullRequest;
import com.potxxx.firstim.message.PullResponse;
import com.potxxx.firstim.service.ChatService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PullRequestHandler extends SimpleChannelInboundHandler<PullRequest> {
    ChatService chatService;
    public PullRequestHandler(ChatService chatService){
        this.chatService = chatService;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PullRequest pullRequest) throws Exception {
        log.info("tcpgate PullRequest :{}",pullRequest.getMaxMsgId());
        try{
            PullResponse pullResponse = chatService.pullMsg(pullRequest);
            log.info("pullResponse {}",pullResponse.getMsgs().size());
            channelHandlerContext.writeAndFlush(pullResponse);
        }catch (Exception e){
            log.info("PullRequest error {}",e.toString());
        }

    }
}
