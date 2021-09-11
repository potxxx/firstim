package com.potxxx.firstim.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {

    public int messageType;
    public static final int PING = 0;
    public static final int PONG = 1;
    public static final int LOGIN = 2;
    public static final int LOGOUT = 3;
    public static final int C2CSENDREQUEST = 4;
    public static final int C2CSENDRESPONSE = 5;

    public static Message parseFromBytes(int messageType, byte[] bytes) {
        switch (messageType){
            case PING:
                return Ping.parseFrom(bytes);
            case PONG:
                return Pong.parseFrom(bytes);
            case LOGIN:
                return Login.parseFrom(bytes);
            case LOGOUT:
                return Logout.parseFrom(bytes);
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
