package com.potxxx.firstim.ipconfig;

import com.alibaba.dubbo.config.annotation.Reference;
import com.potxxx.firstim.service.TcpGateAddrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("ipconfig")
public class IpConfigController {

    @Reference(loadbalance = "consistenthash",retries = 3)
    TcpGateAddrService tcpGateAddrService;

    @GetMapping
    public String getTcpGateAdd(@RequestParam String useId){
        log.info("useID:{} get",useId);
        return tcpGateAddrService.getTcpGateAddr(useId);
    }

}
