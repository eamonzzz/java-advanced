package com.eamon.javadesignpatterns.strategy.payment.v1;

import org.junit.Test;

/**
 * @author eamon.zhang
 * @date 2019-11-06 上午9:41
 */
public class OrderTest {

    @Test
    public void test() {
        String orderId = "123";
        String payType = "aliPay";
        long amount = 200;
        // 创建订单
        Order order = new Order(orderId, payType, amount);
        // 支付
        order.pay();
    }

}