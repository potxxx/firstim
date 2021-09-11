package com.potxxx.firstim.dataServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestContoller {

    @Autowired
    DataServiceImpl dataService;

    @GetMapping
    public String getTest(){
//        return "hello";
        return dataService.findLatestCIdByFromAndTo("1","2");
    }

}
