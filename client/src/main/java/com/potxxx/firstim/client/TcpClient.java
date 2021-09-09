package com.potxxx.firstim.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TcpClient {

    private String tcpGateAddr;
    private NioEventLoopGroup group;
    private SocketChannel socketChannel;
    private Bootstrap bootstrap;

    @Value("${clienttotcpgate.port}")
    private int tcpGatePort;

    @PostConstruct
    public void start() {

        //1、获取TCPGate服务地址
        tcpGateAddr = "127.0.0.1:9091";
        //2、与TCPGate建立长连接
        connectToTCPGate();


    }

    void connectToTCPGate(){



    }

    void reconnect(){

    }


}
