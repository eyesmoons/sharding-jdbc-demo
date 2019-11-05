package com.eyesmoons.algorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

public final class SingleKeyModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {

    private int dbCount = 1;

    /**
     * sql 中关键字 匹配符为 =的时候，表的路由函数
     */
    @Override
    public String doEqualSharding(final Collection<String> availableTargetNames, final ShardingValue<Integer> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % dbCount + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

    /**
     * sql 中关键字 匹配符为 in 的时候，表的路由函数  
     */
    @Override
    public Collection<String> doInSharding(final Collection<String> availableTargetNames, final ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Collection<Integer> values = shardingValue.getValues();
        for (Integer value : values) {
            for (String dataSourceName : availableTargetNames) {
                if (dataSourceName.endsWith(value % dbCount + "")) {
                    result.add(dataSourceName);
                }
            }
        }
        return result;
    }

    /**
     * sql 中关键字 匹配符为 between的时候，表的路由函数
     */
    @Override
    public Collection<String> doBetweenSharding(final Collection<String> availableTargetNames, final ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i % dbCount + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    /**
     * 设置database分库的个数
     *
     * @param dbCount
     */
    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }


    public int getDbCount() {
        return dbCount;
    }
}
