package com.potxxx.firstim.chatServer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;
import com.potxxx.firstim.service.ChatService;
import com.potxxx.firstim.service.DataService;
import com.potxxx.firstim.service.TcpGateAddrService;
import org.springframework.stereotype.Component;

@Service
@Component
public class ChatServiceImpl implements ChatService {

    @Reference(loadbalance = "consistenthash",retries = 3)
    DataService dataService;


    @Override
    public C2CSendResponse c2cSendMsg(C2CSendRequest c2CSendRequest) {
        //1、查询当前会话的cid最大值
        Long latestCId = dataService.findLatestCIdByFromAndTo(c2CSendRequest.getFrom(), c2CSendRequest.getTo());
        //2、与入参的preid相等或为第一条数据则入库返回ack
        if(latestCId == null || latestCId.equals(c2CSendRequest.getPreId())){
            if(dataService.insertC2CMsg(c2CSendRequest) != -1){
                return new C2CSendResponse(c2CSendRequest.getFrom(),c2CSendRequest.getTo(), c2CSendRequest.getCId());
            }
        }
        //3、若不相等返回当前会话已接收的cid最大值
        return new C2CSendResponse(c2CSendRequest.getFrom(),c2CSendRequest.getTo(), latestCId);

    }
}
