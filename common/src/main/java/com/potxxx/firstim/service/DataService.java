package com.potxxx.firstim.service;

import com.potxxx.firstim.message.C2CSendRequest;

public interface DataService {

    String findLatestCIdByFromAndTo(String from,String to);
    boolean insertC2CMsg(C2CSendRequest c2CSendRequest);
}
