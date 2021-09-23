package com.potxxx.firstim.dataServer;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class ShardingStringAlgorithm implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String str = preciseShardingValue.getValue();
        int mode = Math.abs(str.hashCode() % collection.size());
        String[] strings = collection.toArray(new String[collection.size()]);
        return strings[mode];
    }
}
