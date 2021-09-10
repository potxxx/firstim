package com.potxxx.firstim.ipconfig;

import com.alibaba.dubbo.config.annotation.Reference;
import com.potxxx.firstim.service.TcpGateAddrService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ipconfig")
public class IpConfigController {

    @Reference(loadbalance = "consistenthash",retries = 3)
    TcpGateAddrService tcpGateAddrService;

    @GetMapping
    public String getTcpGateAdd(@RequestParam String useId){
        return tcpGateAddrService.getTcpGateAddr(useId);
    }

}
