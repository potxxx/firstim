package com.potxxx.firstim.service;

import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.C2CSendResponse;
import com.potxxx.firstim.message.PullRequest;
import com.potxxx.firstim.message.PullResponse;

public interface ChatService {

    C2CSendResponse c2cSendMsg(C2CSendRequest c2CSendRequest);

    PullResponse pullMsg(PullRequest pullRequest);

}
