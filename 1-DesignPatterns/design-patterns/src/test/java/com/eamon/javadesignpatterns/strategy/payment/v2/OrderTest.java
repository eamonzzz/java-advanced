package com.eamon.javadesignpatterns.strategy.payment.v2;

import com.eamon.javadesignpatterns.strategy.payment.v2.paytype.JdPay;
import com.eamon.javadesignpatterns.strategy.payment.v2.paytype.Payment;
import com.eamon.javadesignpatterns.strategy.payment.v2.paytype.WeChatPay;
import org.junit.Test;

/**
 * @author eamon.zhang
 * @date 2019-11-06 上午10:00
 */
public class OrderTest {

    @Test
    public void test() {
        // 创建支付策略
        Payment jdPay = new JdPay();

        // 创建策略上下文（订单），并将具体的策略实现注入
        String orderId = "123456";
        long amount = 150;
        Order order = new Order(orderId, jdPay, amount);

        // 调用具体支付策略逻辑
        order.pay();
    }

}