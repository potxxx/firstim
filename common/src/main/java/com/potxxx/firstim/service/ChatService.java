package com.potxxx.firstim.service;

import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;

public interface ChatService {

    C2CSendResponse c2cSendMsg(C2CSendRequest c2CSendRequest);

}
