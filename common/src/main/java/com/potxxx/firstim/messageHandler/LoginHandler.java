package com.potxxx.firstim.messageHandler;

import com.potxxx.firstim.common.SpringUtil;
import com.potxxx.firstim.message.Login;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<Login> {
    private  RedisTemplate redisTemplate;
    private  String serverAddr;
    private ConcurrentHashMap<String, Channel> map;

    public LoginHandler(String serverAddr,RedisTemplate redisTemplate,ConcurrentHashMap<String, Channel> map){
        this.serverAddr = serverAddr;
        this.redisTemplate = redisTemplate;
        this.map = map;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Login login) throws Exception {
        log.info("login:{}",login.getUseId());
        redisTemplate.opsForValue().set(login.getUseId(),serverAddr);
        map.put(login.getUseId(),channelHandlerContext.channel());
    }
}
