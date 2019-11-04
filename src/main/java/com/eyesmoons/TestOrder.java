package com.eyesmoons;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eyesmoons.entity.Order;
import com.eyesmoons.entity.OrderExample;
import com.eyesmoons.service.OrderService;
import com.google.common.collect.Lists;

public class TestOrder {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-database.xml");
        OrderService orderService = ctx.getBean(OrderService.class);
        testGetAllOrder(orderService);
        ctx.close();
    }

    /**
     * 描述: 添加订单数据
     * @author shengyu
     * @param orderService 
     * @date:2019年11月4日 下午5:54:30
     */
    private static void testAddOrder(OrderService orderService){
    	orderService.deleteAll();
    	
    	orderService.addOrder(buildOrder(1, 1, "NEW"));
        orderService.addOrder(buildOrder(2, 1, "NEW"));
        orderService.addOrder(buildOrder(3, 1, "NEW"));
        orderService.addOrder(buildOrder(4, 1, "NEW"));

        orderService.addOrder(buildOrder(1, 2, "NEW"));
        orderService.addOrder(buildOrder(2, 2, "NEW"));
        orderService.addOrder(buildOrder(3, 2, "NEW"));
        orderService.addOrder(buildOrder(4, 2, "NEW"));

        orderService.addOrder(buildOrder(5, 3, "NEW"));
        orderService.addOrder(buildOrder(6, 3, "NEW"));
        orderService.addOrder(buildOrder(7, 3, "NEW"));
        orderService.addOrder(buildOrder(8, 3, "NEW"));
    }
    
    /**
     * 描述: 更新单条订单数据
     * @author shengyu
     * @param orderService 
     * @date:2019年11月4日 下午5:54:44
     */
    private static void testUpdateOrder(OrderService orderService){
    	Order o = new Order();
        o.setOrderId(1);
        o.setUserId(1);
        o.setStatus("UPDATED");
        orderService.update(o);
    }
    
    /**
     * 描述: 批量更新订单数据
     * @author shengyu 
     * @date:2019年11月4日 下午5:55:54
     */
    private static void testupdateOrders(OrderService orderService){
    	orderService.updateOrders(Lists.newArrayList(1, 2), "UPDATED");
    }
    
    /**
     * 描述: 返回所有订单
     * @author shengyu
     * @param orderService 
     * @date:2019年11月4日 下午5:57:00
     */
    private static void testGetAllOrder(OrderService orderService){
    	List<Order> orders = orderService.getAllOrder();
    	orders.forEach((order)->{
    		System.out.println(order.toString());
    	});
    }
    
    /**
     * 描述: 删除所有订单
     * @author shengyu
     * @param orderService 
     * @date:2019年11月4日 下午5:58:23
     */
    public static void testDeleteAll(OrderService orderService){
    	orderService.deleteAll();
    }
    
    /**
     * 描述: 查询订单总数
     * @author shengyu
     * @param orderService 
     * @date:2019年11月4日 下午5:59:52
     */
    public static void testGetCount(OrderService orderService){
    	OrderExample example = new OrderExample();
        int count = orderService.getCount(example);
        System.out.println(String.format("count => %d", count));
        example.createCriteria()
                .andUserIdBetween(1, 2)
                .andOrderIdBetween(2, 4);
        count = orderService.getCount(example);
        System.out.println(String.format("count => %d", count));
    }
    
    /**
     * 描述: 返回最大订单号
     * @author shengyu
     * @param orderService 
     * @date:2019年11月4日 下午6:01:01
     */
    public static void testMaxCount(OrderService orderService){
    	int maxOrderId = orderService.getMaxOrderId(null);
        System.out.println(String.format("maxOrderId => %d", maxOrderId));
        OrderExample example = new OrderExample();
        example.createCriteria()
                .andUserIdEqualTo(2);
        maxOrderId = orderService.getMaxOrderId(example);
        System.out.println(String.format("maxOrderId => %d", maxOrderId));
    }
    
    /**
     * 描述: 返回最大用户id
     * @author shengyu
     * @param orderService 
     * @date:2019年11月4日 下午6:01:56
     */
    public static void testGetMaxUserId(OrderService orderService){
    	int maxUserId = orderService.getMaxUserId(null);
        System.out.println(String.format("maxUserId => %d", maxUserId));
        OrderExample example = new OrderExample();
        example.createCriteria()
                .andOrderIdBetween(3, 6);
        maxUserId = orderService.getMaxUserId(example);
        System.out.println(String.format("maxUserId => %d", maxUserId));
    }

    private static Order buildOrder(int orderId, int userId, String status) {
        Order o = new Order();
        o.setOrderId(orderId);
        o.setUserId(userId);
        o.setStatus(status);
        return o;
    }
}
