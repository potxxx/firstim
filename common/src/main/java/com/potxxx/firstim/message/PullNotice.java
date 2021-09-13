package com.potxxx.firstim.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potxxx.firstim.message.proto.MessageProto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PullNotice extends Message {

    String useId;

    public PullNotice(String useId){
        messageType = PULLNOTICE;
        this.useId = useId;
    }

    public PullNotice(int messageType,String useId){
        this.messageType = messageType;
        this.useId = useId;
    }

    private PullNotice(MessageProto.pull_notice_proto proto){
        messageType = proto.getMessageType();
        useId = proto.getUseId();
    }

    public static PullNotice parseFrom(byte[] bytes) {
        try {
            return new PullNotice(MessageProto.pull_notice_proto.parseFrom(bytes));
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public int getMessageType() {
        return PULLNOTICE;
    }

    @Override
    public byte[] toByteArray() {
        return MessageProto.pull_notice_proto.newBuilder()
                .setMessageType(PULLNOTICE)
                .setUseId(useId)
                .build()
                .toByteArray();
    }
}
