package com.potxxx.firstim.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Ping extends Message {
    public static Ping parseFrom(byte[] bytes) {
        Ping ping = new Ping();
        ping.setMessageType(PING);
        return ping;
    }

    public Ping(){
        messageType = PING;
    }
    @Override
    public int getMessageType() {
        return PING;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
