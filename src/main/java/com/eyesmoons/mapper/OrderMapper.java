package com.eyesmoons.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eyesmoons.entity.Order;
import com.eyesmoons.entity.OrderExample;

import tk.mybatis.mapper.common.Mapper;

public interface OrderMapper extends Mapper<Order> {

    int insertBatch(List<Order> orders);

    int countByExample(OrderExample example);

    int maxOrderIdByExample(OrderExample example);

    int minOrderIdByExample(OrderExample example);

    int maxUserIdByExample(OrderExample example);

    int minUserIdByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    List<Order> selectByExample(OrderExample example);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);
}