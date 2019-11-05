package com.eyesmoons.algorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

public final class SingleKeyModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

    private int tableCount = 1;

    /**
     * sql 中 = 操作时，table的映射
     */
    @Override
    public String doEqualSharding(final Collection<String> availableTargetNames, final ShardingValue<Integer> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % tableCount + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

    /**
     * sql 中 in 操作时，table的映射
     */
    @Override
    public Collection<String> doInSharding(final Collection<String> availableTargetNames, final ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());
        Collection<Integer> values = shardingValue.getValues();
        for (Integer value : values) {
            for (String tableNames : availableTargetNames) {
                if (tableNames.endsWith(value % tableCount + "")) {
                    result.add(tableNames);
                }
            }
        }
        return result;
    }

    /**
     * sql 中 between 操作时，table的映射
     */
    @Override
    public Collection<String> doBetweenSharding(final Collection<String> availableTargetNames, final ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i % tableCount + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    /**
     * 设置分表的个数
     *
     * @param tableCount
     */
    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public int getTableCount() {
        return tableCount;
    }
}
