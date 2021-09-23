package com.potxxx.firstim.dataServer;

import com.potxxx.firstim.message.C2CSendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestContoller {

    @Autowired
    DataServiceImpl dataService;

    @GetMapping("/{id}")
    public long getTest(@PathVariable(name = "id") String id){
//        return "hello";
         return dataService.insertC2CMsg(new C2CSendRequest("1",id,13L,14L,"this is the first content"));
//        return dataService.findLatestCIdByFromAndTo("1","2");
    }

}
