package com.potxxx.firstim.dataServer;

import com.potxxx.firstim.message.C2CSendRequest;
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
    public long getTest(){
//        return "hello";
//        dataService.insertC2CMsg(new C2CSendRequest("1","2","13","14","this is the first content"));
        return dataService.findLatestCIdByFromAndTo("1","2");
    }

}
