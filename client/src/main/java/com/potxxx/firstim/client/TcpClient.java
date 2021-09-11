package com.potxxx.firstim.client;

import com.potxxx.firstim.message.*;
import com.potxxx.firstim.messageHandler.C2CSendResponseHandler;
import com.potxxx.firstim.messageHandler.HeartBeatHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TcpClient {

    private NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;

    @Value("${clienttotcpgate.port}")
    private int clienttcpGatePort;

    @Value("${client.allIdleTimeSeconds}")
    private int allIdleTimeSeconds;

    @Value("${client.Retry}")
    private int clientRetry;

    @Value("${client.useId}")
    private String clientUseId;

    private int retryCnt = 0;

    private String tcpGateIp;

    private int tcpGatePort;

    @Autowired
    IpConfigClient ipConfigClient;

    @PostConstruct
    public void start() {

        //1、获取TCPGate服务地址
        String[] temp = ipConfigClient.getTcpGateAdd(clientUseId).split(":");
        tcpGateIp = temp[0];
        tcpGatePort = Integer.parseInt(temp[1]);
        //2、与TCPGate建立长连接
        connectToTCPGate();
        //3、登录TCPGate
        loginTcpGate();
        sendMsg(new C2CSendRequest("1","2","xs","cas","hello"));
    }

    public void loginTcpGate(){
        sendMsg(new Login(clientUseId));
    }

    public void sendMsg(Message message){
        tryReconnect();
        channel.writeAndFlush(message);
    }

    void connectToTCPGate(){
        bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,30000)
//                .localAddress(clienttcpGatePort)
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
        retryCnt = 0;
        bootstrap.connect(tcpGateIp,tcpGatePort).addListener((ChannelFuture f)->{
            if(f.isSuccess()){
                channel = f.channel();
                retryCnt = 0;
                log.info("连接成功");
            }else{
                if(retryCnt > clientRetry){
                    log.info("连接重试超过3次，请稍后重试");
                    return;
                }
                retryCnt++;
                connectToTCPGate();
            }
        });
    }

    void tryReconnect(){
        if(channel != null&&channel.isActive()){
            return;
        }
        log.info("已断开连接，尝试重新建立连接，请稍等...");
        start();
    }

    void close(){
        if(channel != null){
            channel.close();
        }
    }


}
