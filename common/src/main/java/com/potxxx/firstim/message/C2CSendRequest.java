package com.potxxx.firstim.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potxxx.firstim.message.proto.MessageProto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class C2CSendRequest extends Message{

    String from;
    String to;
    String preId;
    String cId;
    String content;

    public C2CSendRequest(String from,String to,String preId,String cId,String content){
        messageType = C2CSENDREQUEST;
        this.from = from;
        this.to = to;
        this.preId = preId;
        this.cId = cId;
        this.content = content;
    }

    public static C2CSendRequest parseFrom(byte[] bytes) {
        try {
            MessageProto.c2c_send_request_proto ob = MessageProto.c2c_send_request_proto.parseFrom(bytes);
            C2CSendRequest c2CSendRequest = new C2CSendRequest();
            c2CSendRequest.setMessageType(ob.getMessageType());
            c2CSendRequest.setFrom(ob.getFrom());
            c2CSendRequest.setTo(ob.getTo());
            c2CSendRequest.setPreId(ob.getPreId());
            c2CSendRequest.setCId(ob.getCId());
            c2CSendRequest.setContent(ob.getContent());
            return c2CSendRequest;
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }

    }

    @Override
    public int getMessageType() {
        return C2CSENDREQUEST;
    }

    @Override
    public byte[] toByteArray() {
        return MessageProto.c2c_send_request_proto.newBuilder()
                .setMessageType(messageType)
                .setFrom(from)
                .setTo(to)
                .setPreId(preId)
                .setCId(cId)
                .setContent(content)
                .build()
                .toByteArray();
    }
}
