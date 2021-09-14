package com.potxxx.firstim.service;

import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.message.C2CSendRequest;

import java.util.List;

public interface DataService {

    Long findLatestCIdByFromAndTo(String from,String to);
    int insertC2CMsg(C2CSendRequest c2CSendRequest);


    List<Msg> getNewMsgByUserIdAndMaxMsgId(String useId,Long maxMsgId);
}
