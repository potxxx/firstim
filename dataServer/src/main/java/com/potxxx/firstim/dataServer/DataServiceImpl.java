package com.potxxx.firstim.dataServer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.potxxx.firstim.Enum.MsgType;
import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.dataServer.Mapper.MsgMapper;
import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.service.DataService;
import com.potxxx.firstim.service.GenIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service
@Component
public class DataServiceImpl implements DataService {

    @Autowired
    MsgMapper msgMapper;

    @Reference(loadbalance = "consistenthash",retries = 3)
    GenIDService genIDService;

    @Override
    public Long findLatestCIdByFromAndTo(String from, String to) {

        return msgMapper.findLatestCIdByFromAndTo(from, to);
    }

    @Override
    public int insertC2CMsg(C2CSendRequest c2CSendRequest) {

        Msg msg = new Msg();
        msg.setMsgId(genIDService.getID());
        msg.setMsgCId(c2CSendRequest.getCId());
        msg.setMsgTo(c2CSendRequest.getTo());
        msg.setMsgFrom(c2CSendRequest.getFrom());
        msg.setMsgType(MsgType.C2C.toString());
        msg.setDelivered("false");
        msg.setMsgContent(c2CSendRequest.getContent());

        return msgMapper.insert(msg);
    }

    @Override
    public List<Msg> getNewMsgByUserIdAndMaxMsgId(String useId, Long maxMsgId) {
        return msgMapper.getNewMsgByUserIdAndMaxMsgId(useId,maxMsgId);
    }

    @Override
    public int updateDelivered(String useId, Long maxMsgId) {
        return msgMapper.updateDelivered(useId,maxMsgId);
    }
}
