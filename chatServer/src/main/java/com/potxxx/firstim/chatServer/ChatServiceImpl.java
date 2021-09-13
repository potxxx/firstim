package com.potxxx.firstim.chatServer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;
import com.potxxx.firstim.message.PullNotice;
import com.potxxx.firstim.service.ChatService;
import com.potxxx.firstim.service.DataService;
import com.potxxx.firstim.service.TcpGateAddrService;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
@Component
public class ChatServiceImpl implements ChatService {

    @Reference(loadbalance = "consistenthash",retries = 3)
    DataService dataService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public C2CSendResponse c2cSendMsg(C2CSendRequest c2CSendRequest)  {
        //1、查询当前会话的cid最大值
        Long latestCId = dataService.findLatestCIdByFromAndTo(c2CSendRequest.getFrom(), c2CSendRequest.getTo());
        //2、与入参的preid相等或为第一条数据则入库返回ack
        if(latestCId == null || latestCId.equals(c2CSendRequest.getPreId())){
            if(dataService.insertC2CMsg(c2CSendRequest) != -1){
                //发送拉取消息
                String addr = getUserWhere(c2CSendRequest.getTo());
                addr = "http://"+addr+"/tcpGate/transPullNotice";

                try {
                    restTemplate.postForEntity(new URI(addr),new PullNotice(c2CSendRequest.getTo()),Boolean.class);
                } catch (URISyntaxException e) {
                    log.warn("{}",e.toString());
                }


                return new C2CSendResponse(c2CSendRequest.getFrom(),c2CSendRequest.getTo(), c2CSendRequest.getCId());
            }
        }
        //3、若不相等返回当前会话已接收的cid最大值
        return new C2CSendResponse(c2CSendRequest.getFrom(),c2CSendRequest.getTo(), latestCId);

    }

    private String getUserWhere(String userId ){
        Object addr = redisTemplate.opsForValue().get(userId);
        return addr == null? null:(String) addr;

    }



}
