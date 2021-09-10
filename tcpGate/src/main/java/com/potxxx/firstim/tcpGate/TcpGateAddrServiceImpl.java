package com.potxxx.firstim.tcpGate;

import com.alibaba.dubbo.config.annotation.Service;
import com.potxxx.firstim.service.TcpGateAddrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Component
public class TcpGateAddrServiceImpl  implements TcpGateAddrService {

    @Value("${tcpgateserver.port}")
    private int nettyPort;

    @Override
    public String getTcpGateAddr(String useId) {
        try {
            String localaddr = InetAddress.getLocalHost().getHostAddress();
            return localaddr+":"+String.valueOf(nettyPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}
