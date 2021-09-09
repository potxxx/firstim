package com.potxxx.firstim.tcpGate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class TcpGateApplication {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(TcpGateApplication.class);
        String localaddr = InetAddress.getLocalHost().getHostAddress();
        log.info("----TcpGateWeb服务启动成功 {}----",localaddr);
    }
}
