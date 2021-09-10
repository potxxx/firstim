package com.potxxx.firstim.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FeignClient" ,url = "${Client.IpConfigFeignUrl}")
public interface IpConfigClient {

    @RequestMapping(method = RequestMethod.GET,value = "/ipconfig")
    String getTcpGateAdd(@RequestParam String useId);

}
