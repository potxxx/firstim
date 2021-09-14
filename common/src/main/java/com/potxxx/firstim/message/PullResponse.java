package com.potxxx.firstim.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potxxx.firstim.PO.Msg;
import com.potxxx.firstim.message.proto.MessageProto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


//message pull_response_proto{
//        int32 messageType = 1;
//        string useId = 2;
//        repeated msg_proto msgs = 3;
//        }
//
//        message msg_proto{
//        string fromId = 4;
//        string toId = 5;
//        string msgType = 6;
//        string groupId = 7;
//        int64 msgId = 8;
//        string content = 9;
//        }

@Data
@NoArgsConstructor
@Slf4j
public class PullResponse extends Message{

    String useId;
    List<Msg> msgs;

    public PullResponse(String useId,List<Msg> msgs){
        messageType = PULLRESPONSE;
        this.useId = useId;
        this.msgs = msgs;
    }

    private PullResponse(MessageProto.pull_response_proto proto){
        messageType = proto.getMessageType();
        useId = proto.getUseId();
        msgs = msgProto2msg(proto.getMsgsList());
    }

    private List<Msg> msgProto2msg(List<MessageProto.msg_proto> protos){
        List<Msg> res = new ArrayList<>();
        for(MessageProto.msg_proto proto:protos){
            Msg m = new Msg();
            m.setMsgTo(proto.getToId());
            m.setMsgFrom(proto.getFromId());
            m.setMsgContent(proto.getContent());
            m.setGroupId(proto.getGroupId());
            m.setId((int) proto.getMsgId());
            m.setMsgType(proto.getMsgType());
            res.add(m);
        }
        return res;
    }

    private List<MessageProto.msg_proto> msg2msgProto(List<Msg> msgs){
        List<MessageProto.msg_proto> res = new ArrayList<>();
        for(Msg m:msgs){
            MessageProto.msg_proto p = MessageProto.msg_proto.newBuilder()
                    .setFromId(m.getMsgFrom())
                    .setToId(m.getMsgTo())
                    .setContent(m.getMsgContent())
                    .setMsgId(m.getId())
                    .setGroupId(m.getGroupId())
                    .setMsgType(m.getMsgType())
                    .build();
            res.add(p);
        }
        return res;
    }

    public static PullResponse parseFrom(byte[] bytes) {
        try {
            return new PullResponse(MessageProto.pull_response_proto.parseFrom(bytes));
        } catch (InvalidProtocolBufferException e) {
            log.error(e.toString());
            return null;
        }


    }

    @Override
    public int getMessageType() {
        return PULLRESPONSE;
    }

    @Override
    public byte[] toByteArray() {
        return MessageProto.pull_response_proto.newBuilder()
                .setMessageType(PULLRESPONSE)
                .setUseId(useId)
                .addAllMsgs(msg2msgProto(msgs))
                .build()
                .toByteArray();
    }
}
