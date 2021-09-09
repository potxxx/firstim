package com.potxxx.firstim.client;

import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.MessageCoder;
import com.potxxx.firstim.message.MessageLengthFieldFrameDecoder;
import com.potxxx.firstim.message.Ping;
import com.potxxx.firstim.messageHandler.C2CSendResponseHandler;
import com.potxxx.firstim.messageHandler.HeartBeatHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TcpClient {

    private String tcpGateAddr;
    private NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;

    @Value("${clienttotcpgate.port}")
    private int tcpGatePort;

    @Value("${client.allIdleTimeSeconds}")
    private int allIdleTimeSeconds;

    @PostConstruct
    public void start() {

        //1、获取TCPGate服务地址
        tcpGateAddr = "127.0.0.1:9091";
        //2、与TCPGate建立长连接
        connectToTCPGate();

        sendMsg();
    }

    public void sendMsg(){
        channel.writeAndFlush(new C2CSendRequest("xxw","xzx","12","13","hello"));
    }

    void connectToTCPGate(){
        bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_KEEPALIVE,true)
//                .localAddress(tcpGatePort)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new IdleStateHandler(0,0,allIdleTimeSeconds))
                                .addLast(new MessageLengthFieldFrameDecoder())
                                .addLast(new MessageCoder())
                                .addLast(new HeartBeatHandler())
                                .addLast(new C2CSendResponseHandler());


                    }
                });
        try {
            channel = bootstrap.connect("127.0.0.1",9091).sync().channel();
        } catch (InterruptedException e) {
            log.info("连接发生异常{}",e.toString());
        }

    }

    void reconnect(){

    }


}
