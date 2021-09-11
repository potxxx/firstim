package com.potxxx.firstim.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potxxx.firstim.message.proto.MessageProto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class Logout extends Message{

    String useId;

    public Logout(String useId){
        messageType = LOGIN;
        this.useId = useId;
    }
    public Logout(MessageProto.logout_proto logout){
        messageType = logout.getMessageType();
        useId = logout.getUseId();
    }

    public static Logout parseFrom(byte[] bytes) {
        try {
            return new Logout(MessageProto.logout_proto.parseFrom(bytes));
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public int getMessageType() {
        return LOGOUT;
    }

    @Override
    public byte[] toByteArray() {
        return MessageProto.logout_proto.newBuilder()
                .setMessageType(LOGOUT)
                .setUseId(useId)
                .build()
                .toByteArray();
    }
}