package com.potxxx.firstim.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potxxx.firstim.message.proto.MessageProto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Login extends Message{

    String useId;

    public Login(String useId){
        messageType = LOGIN;
        this.useId = useId;
    }
    public Login(MessageProto.login_proto login){
        messageType = login.getMessageType();
        useId = login.getUseId();
    }

    public static Login parseFrom(byte[] bytes) {
        try {
            return new Login(MessageProto.login_proto.parseFrom(bytes));
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public int getMessageType() {
        return LOGIN;
    }

    @Override
    public byte[] toByteArray() {
    return MessageProto.login_proto.newBuilder()
            .setMessageType(LOGIN)
            .setUseId(useId)
            .build()
            .toByteArray();
    }
}
