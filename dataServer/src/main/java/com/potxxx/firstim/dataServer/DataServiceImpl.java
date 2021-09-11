package com.potxxx.firstim.dataServer;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.dataServer.Mapper.MsgMapper;
import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class DataServiceImpl implements DataService {

    @Autowired
    MsgMapper msgMapper;

    @Override
    public String findLatestCIdByFromAndTo(String from, String to) {
        return msgMapper.findLatestCIdByFromAndTo(from, to);
    }

    @Override
    public boolean insertC2CMsg(C2CSendRequest c2CSendRequest) {
        return false;
    }
}
