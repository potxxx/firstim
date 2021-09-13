package com.potxxx.firstim.tcpGate;

import ch.qos.logback.classic.Logger;
import com.potxxx.firstim.message.PullNotice;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tcpGate")
public class TcpGateController {

    @PostMapping("/transPullNotice")
    public boolean transPullMsg(@RequestBody PullNotice pullNotice){
        log.info("get transPullMsg:{}",pullNotice.getUseId());
        Channel channel = ChannelCache.map.get(pullNotice.getUseId());
        if(channel == null) return false;
        channel.writeAndFlush(pullNotice);
        return true;
    }
}
