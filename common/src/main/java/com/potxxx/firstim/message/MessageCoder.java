package com.potxxx.firstim.message;

import com.potxxx.firstim.message.C2CSendRequest;
import com.potxxx.firstim.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
/*
* 协议头 |magicCode4|type4|bodyLength4|body|
* */
@Slf4j
public class MessageCoder extends ByteToMessageCodec<Message> {
    public static final int MAGICCODE = 0xFAFAFAFA;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(MAGICCODE);
        byteBuf.writeInt(message.getMessageType());
        byte[] bytes = message.toByteArray();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicCode = byteBuf.readInt();
        if(magicCode != MAGICCODE) return;
        int messageType = byteBuf.readInt();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);
        Message message = Message.parseFromBytes(messageType,bytes);
        list.add(message);
    }
    //test
//    public static void main(String[] args) {
//        MessageCoder messageCoder = new MessageCoder();
//        C2CSendRequest c2CSendRequest = new C2CSendRequest();
//        c2CSendRequest.setMessageType(2);
//        c2CSendRequest.setFrom("hihao");
//        c2CSendRequest.setTo("hi");
//        c2CSendRequest.setPreId("12");
//        c2CSendRequest.setCId("13");
//        c2CSendRequest.setContent("hello");
//        ByteBuf byteBuf = Unpooled.buffer();
//        try {
//            messageCoder.encode(null,c2CSendRequest,byteBuf);
//            List<Object> list = new ArrayList<>();
//            messageCoder.decode(null,byteBuf,list);
//            Message message = (Message) list.get(0);
//            log.info("{}",message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
