package com.potxxx.firstim.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Pong extends Message {
    public static Pong parseFrom(byte[] bytes) {
        Pong pong = new Pong();
        pong.setMessageType(PONG);
        return pong;
    }

    public Pong(){
        messageType = PONG;
    }

    @Override
    public int getMessageType() {
        return PONG;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
