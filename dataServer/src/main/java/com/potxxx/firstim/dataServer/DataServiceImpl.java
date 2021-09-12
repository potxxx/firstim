package com.potxxx.firstim.dataServer;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.potxxx.firstim.Enum.MsgType;
import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.dataServer.Mapper.MsgMapper;
import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.Login;
import com.potxxx.firstim.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Service
@Component
public class DataServiceImpl implements DataService {

    @Autowired
    MsgMapper msgMapper;

    @Override
    public Long findLatestCIdByFromAndTo(String from, String to) {
        return msgMapper.findLatestCIdByFromAndTo(from, to);
    }

    @Override
    public int insertC2CMsg(C2CSendRequest c2CSendRequest) {

        Msg msg = new Msg();
        msg.setMsgId(UUID.randomUUID().toString());
        msg.setMsgCId(c2CSendRequest.getCId());
        msg.setMsgTo(c2CSendRequest.getTo());
        msg.setMsgFrom(c2CSendRequest.getFrom());
        msg.setMsgType(MsgType.C2C.toString());
        msg.setDelivered("false");
        msg.setMsgContent(c2CSendRequest.getContent());

        return msgMapper.insert(msg);
    }
}
