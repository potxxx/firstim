package com.potxxx.firstim.tcpGate;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelCache {
    static ConcurrentHashMap<String, Channel> map = new ConcurrentHashMap<>();
}
