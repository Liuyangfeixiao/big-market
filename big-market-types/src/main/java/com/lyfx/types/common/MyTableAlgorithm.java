package com.lyfx.types.common;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * @author Yangfeixaio Liu
 * @time 2024/12/24 下午4:42
 * @description 自定义分表算法
 */

public class MyTableAlgorithm implements StandardShardingAlgorithm {
    
    private Properties props;
    
    @Override
    public String getType() {
        return StandardShardingAlgorithm.super.getType();
    }
    
    public Properties getProps() {
        return props;
    }
    
    @Override
    public void init(Properties props) {
        this.props = props;
    }
    
    @Override
    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        int tableSize = collection.size();
        // 真实表前缀
        String tablePrefix = preciseShardingValue.getDataNodeInfo().getPrefix();
        // 分片键的值
        long hashCode = preciseShardingValue.getValue().hashCode();
        // 真实分片位置
        long mod = ((hashCode ^ (hashCode >>> (1 << tableSize))) & (tableSize - 1));
        return tablePrefix + "00" + mod;
    }
    
    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        return null;
    }
}
