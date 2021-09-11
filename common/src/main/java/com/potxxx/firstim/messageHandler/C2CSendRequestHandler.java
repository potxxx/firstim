package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.common.SpringUtil;
import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;
import com.potxxx.firstim.service.ChatService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class C2CSendRequestHandler extends SimpleChannelInboundHandler<C2CSendRequest> {

    private ChatService chatService;

    public C2CSendRequestHandler(ChatService chatService){
        this.chatService = chatService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, C2CSendRequest c2CSendRequest) throws Exception {
        log.info("get {}",c2CSendRequest.getContent());
        try{
            C2CSendResponse c2CSendResponse = chatService.c2cSendMsg(c2CSendRequest);
            channelHandlerContext.writeAndFlush(c2CSendResponse);
        }catch (Exception e){
            channelHandlerContext.writeAndFlush(new C2CSendResponse("11","12","error"));
            log.error("{}",e.toString());
        }
    }
}
