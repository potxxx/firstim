package com.potxxx.firstim.tcpGate;

import com.potxxx.firstim.message.MessageCoder;
import com.potxxx.firstim.message.MessageLengthFieldFrameDecoder;
import com.potxxx.firstim.messageHandler.C2CSendRequestHandler;
import com.potxxx.firstim.messageHandler.PingHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class TcpGateServer {

    @Value("${tcpgateserver.port}")
    private int nettyPort;
    @Value("${tcpgateserver.readerIdleTimeSeconds}")
    private int readerIdleTimeSeconds;


    private NioEventLoopGroup boss;
    private NioEventLoopGroup works;

    @PostConstruct
    void start(){
        boss = new NioEventLoopGroup();
        works = new NioEventLoopGroup();
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
                                //服务端在三倍客户端心跳时间未收到消息则主动断开连接
                                .addLast(new IdleStateHandler(readerIdleTimeSeconds,0,0))
                                .addLast(new MessageLengthFieldFrameDecoder())
                                .addLast(new MessageCoder())
                                .addLast(new PingHandler())
                                .addLast(new C2CSendRequestHandler());
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
