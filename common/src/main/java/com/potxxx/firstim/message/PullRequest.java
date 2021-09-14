package com.potxxx.firstim.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potxxx.firstim.message.proto.MessageProto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class PullRequest extends Message {

    String useId;
    Long  maxMsgId;

    public PullRequest(String useId,Long  maxMsgId){
        messageType = PULLREQUEST;
        this.useId = useId;
        this.maxMsgId = maxMsgId;
    }

    private PullRequest(MessageProto.pull_request_proto proto){
        messageType = proto.getMessageType();
        useId = proto.getUseId();
        maxMsgId = proto.getMaxMsgId();
    }

    public static PullRequest parseFrom(byte[] bytes) {
        try {
            return new PullRequest(MessageProto.pull_request_proto.parseFrom(bytes));
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public int getMessageType() {
        return PULLREQUEST;
    }

    @Override
    public byte[] toByteArray() {
        return MessageProto.pull_request_proto.newBuilder()
                .setMessageType(PULLREQUEST)
                .setUseId(useId)
                .setMaxMsgId(maxMsgId)
                .build()
                .toByteArray();
    }
}
