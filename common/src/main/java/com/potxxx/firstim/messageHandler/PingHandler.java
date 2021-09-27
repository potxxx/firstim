package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.message.Ping;
import com.potxxx.firstim.message.Pong;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class PingHandler extends SimpleChannelInboundHandler<Ping> {

    private ConcurrentHashMap<String, Channel> map;
    private RedisTemplate redisTemplate;

    public PingHandler(ConcurrentHashMap<String, Channel> map,RedisTemplate redisTemplate){
        this.map = map;
        this.redisTemplate = redisTemplate;
    }

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

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        String name = "";
        for(Map.Entry<String, Channel> e : map.entrySet()){
            if(e.getValue().equals(ctx.channel())){
                name = e.getKey();
            }
        }
        map.remove(name);
        redisTemplate.delete(name);

        super.channelInactive(ctx);
    }
}
