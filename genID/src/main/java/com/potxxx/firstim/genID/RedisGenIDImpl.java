package com.potxxx.firstim.genID;

import com.alibaba.dubbo.config.annotation.Service;
import com.potxxx.firstim.service.GenIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Service
@Component
public class RedisGenIDImpl implements GenIDService {

    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    void init(){
        redisTemplate.opsForValue().setIfAbsent(GenIDConstants.REDISGENID,0);
    }


    @Override
    public Long getID() {
        return redisTemplate.opsForValue().increment(GenIDConstants.REDISGENID);
    }
}
