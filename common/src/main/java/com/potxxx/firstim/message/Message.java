package com.potxxx.firstim.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message {

    public int messageType;
    public static final int C2CSENDREQUEST = 2;
    public static final int C2CSENDRESPONSE = 3;

    public static Message parseFromBytes(int messageType, byte[] bytes) {
        switch (messageType){
            case  C2CSENDREQUEST:
                return C2CSendRequest.parseFrom(bytes);
            case C2CSENDRESPONSE:
                return C2CSendResponse.parseFrom(bytes);
        }
        return null;
    }

    public abstract int getMessageType();


    public abstract byte[] toByteArray();
}
