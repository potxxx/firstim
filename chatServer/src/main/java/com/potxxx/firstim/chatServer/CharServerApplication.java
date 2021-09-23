package com.potxxx.firstim.chatServer;

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
@ComponentScan(value = "com.potxxx.firstim.common")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CharServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CharServerApplication.class,args);
        String localaddr = null;
        try {
            localaddr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.info("----CharServer服务启动成功 {}----",localaddr);
    }
}
