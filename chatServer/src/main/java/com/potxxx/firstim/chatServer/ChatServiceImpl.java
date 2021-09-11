package com.potxxx.firstim.chatServer;

import com.alibaba.dubbo.config.annotation.Service;
import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;
import com.potxxx.firstim.service.ChatService;
import org.springframework.stereotype.Component;

@Service
@Component
public class ChatServiceImpl implements ChatService {
    @Override
    public C2CSendResponse c2cSendMsg(C2CSendRequest c2CSendRequest) {
        //1、查询当前会话的cid最大值

        //2、与入参的preid相等则入库返回ack

        //3、若不相等返回当前会话的cid最大值


        return new C2CSendResponse(c2CSendRequest.getFrom(),c2CSendRequest.getTo(),"testok");
    }
}
