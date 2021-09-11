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
public class C2CSendResponse extends Message{

    String from;
    String to;
    String ackId;   //error为ack失败，否则为已成功接受的消息cid

    public C2CSendResponse(String from,String to,String ackId){
        messageType = C2CSENDRESPONSE;
        this.from = from;
        this.to = to;
        this.ackId = ackId;
    }

    public static C2CSendResponse parseFrom(byte[] bytes) {
        try {
            MessageProto.c2c_send_response_proto ob = MessageProto.c2c_send_response_proto.parseFrom(bytes);
            C2CSendResponse c2CSendRequest = new C2CSendResponse();
            c2CSendRequest.setMessageType(ob.getMessageType());
            c2CSendRequest.setFrom(ob.getFrom());
            c2CSendRequest.setTo(ob.getTo());
            c2CSendRequest.setAckId(ob.getAckId());
            return c2CSendRequest;
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public int getMessageType() {
        return C2CSENDRESPONSE;
    }

    @Override
    public byte[] toByteArray() {
        return MessageProto.c2c_send_response_proto.newBuilder()
                .setMessageType(messageType)
                .setFrom(from)
                .setTo(to)
                .setAckId(ackId)
                .build()
                .toByteArray()
                ;
    }
}
