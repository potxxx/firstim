package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.C2CSendResponse;
import com.potxxx.firstim.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class C2CSendResponseHandler extends SimpleChannelInboundHandler<C2CSendResponse> {
    ConcurrentHashMap<String, TreeMap<Long, Message>> ackMap;

    public  C2CSendResponseHandler(ConcurrentHashMap<String, TreeMap<Long, Message>> ackMap){
        this.ackMap = ackMap;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, C2CSendResponse c2CSendResponse) throws Exception {
        log.info("from server ackID:{}",c2CSendResponse.getAckId());
        if(c2CSendResponse.getAckId() == -1){
            return;
        }
        synchronized (ackMap){
            TreeMap<Long, Message> acklist = ackMap.get(c2CSendResponse.getTo());
            while(!acklist.isEmpty()&&acklist.firstEntry().getKey() <= c2CSendResponse.getAckId()){
                acklist.remove(acklist.firstEntry().getKey());
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("{}",cause.toString());
    }
}
