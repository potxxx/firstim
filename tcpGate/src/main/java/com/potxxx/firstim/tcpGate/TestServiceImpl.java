package com.potxxx.firstim.tcpGate;

import com.alibaba.dubbo.config.annotation.Service;
import com.potxxx.firstim.service.TestService;
import org.springframework.stereotype.Component;

@Service
@Component
public class TestServiceImpl implements TestService {
    @Override
    public int getRandomNum(int key) {
        return key + 10010;
    }
}
