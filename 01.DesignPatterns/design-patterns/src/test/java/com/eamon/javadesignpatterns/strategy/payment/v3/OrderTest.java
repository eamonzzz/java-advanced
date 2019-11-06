package com.eamon.javadesignpatterns.strategy.payment.v3;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author eamon.zhang
 * @date 2019-11-06 上午10:43
 */
public class OrderTest {

    @Test
    public void test() {
        // 前端传入的参数
        String orderId = "01000005";
        String payType = PayStrategyFactory.ALI_PAY;
        long amount = 190;

        // 创建策略上下文（订单），并将具体的策略实现注入
        Order order = new Order(orderId, amount);
        // 实际情况是 在支付的时候选择支付方式，因为有可能先提交了订单，后面再付款
        order.pay(payType);
    }

}