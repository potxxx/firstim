package com.potxxx.firstim.service;

import com.potxxx.firstim.message.C2CSendRequest;

public interface DataService {

    Long findLatestCIdByFromAndTo(String from,String to);
    int insertC2CMsg(C2CSendRequest c2CSendRequest);
}
