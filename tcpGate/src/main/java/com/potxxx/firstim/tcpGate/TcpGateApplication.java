package com.potxxx.firstim.tcpGate;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@EnableDubbo
@SpringBootApplication
@ComponentScan({"com.potxxx.firstim.common","com.potxxx.firstim.tcpGate"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class TcpGateApplication {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(TcpGateApplication.class,args);
        String localaddr = InetAddress.getLocalHost().getHostAddress();
        log.info("----TcpGateWeb服务启动成功 {}----",localaddr);
    }
}
