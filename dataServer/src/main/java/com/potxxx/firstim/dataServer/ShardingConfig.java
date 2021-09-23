package com.potxxx.firstim.dataServer;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {SpringBootConfiguration.class})
@MapperScan(basePackages = {"com.potxxx.firstim.dataServer.Mapper"})
public class ShardingConfig {

}
