package com.potxxx.firstim.message;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageLengthFieldFrameDecoder extends LengthFieldBasedFrameDecoder {
    public MessageLengthFieldFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
    //处理粘包半包|magicCode4|type4|bodyLength4|body|
    public MessageLengthFieldFrameDecoder(){
        super(65535,8,4,0,0);
    }
}
