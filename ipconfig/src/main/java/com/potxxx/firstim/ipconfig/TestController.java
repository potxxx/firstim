package com.potxxx.firstim.ipconfig;

import com.alibaba.dubbo.config.annotation.Reference;
import com.potxxx.firstim.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Reference(loadbalance = "consistenthash",retries = 3)
    TestService testService;

    @GetMapping
    public int dubboTest(){
        return testService.getRandomNum(12);
    }

}
