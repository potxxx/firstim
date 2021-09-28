package com.potxxx.firstim.tcpGate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.potxxx.firstim.message.MessageCoder;
import com.potxxx.firstim.message.MessageLengthFieldFrameDecoder;
import com.potxxx.firstim.messageHandler.*;
import com.potxxx.firstim.service.ChatService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class TcpGateServer {

    @Value("${tcpgateserver.port}")
    private int nettyPort;
    @Value("${tcpgateserver.readerIdleTimeSeconds}")
    private int readerIdleTimeSeconds;
    @Value("${server.port}")
    private int serverport;
    @Autowired
    RedisTemplate redisTemplate;

    @Reference(loadbalance = "consistenthash",retries = 3)
    ChatService chatService;

    private String serverAddr;

    private NioEventLoopGroup boss;
    private NioEventLoopGroup works;

    @PostConstruct
    void start(){
        try {
            serverAddr = InetAddress.getLocalHost().getHostAddress()+":"+String.valueOf(serverport);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        boss = new NioEventLoopGroup();
        works = new NioEventLoopGroup();
        EventExecutorGroup doGroup = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors()*2);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,works)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,30000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(readerIdleTimeSeconds,0,0))
                                .addLast(new MessageLengthFieldFrameDecoder())
                                .addLast(new MessageCoder())
                                .addLast(new PingHandler(ChannelCache.map,redisTemplate))
                                .addLast(doGroup, new RateLimitHandler())
                                .addLast(new LoginHandler(serverAddr,redisTemplate,ChannelCache.map))
                                .addLast(doGroup, new C2CSendRequestHandler(chatService))
                                .addLast(doGroup, new PullRequestHandler(chatService))

                        ;
                    }
                });
            serverBootstrap.bind(nettyPort).addListener((f)->{
                if(f.isSuccess()){
                    log.info("----tcpGateServer启动成功----");
                }else{
                    log.error("----tcpGateServer启动失败 {}----",f.cause().getCause().toString());
                }
            });
    }

    @PreDestroy
    public void destroy(){
        boss.shutdownGracefully().syncUninterruptibly();
        works.shutdownGracefully().syncUninterruptibly();
        log.info("----tcpGateServer成功关闭----");

    }

}
