package com.eamon.javadesignpatterns.strategy.payment.v3.paytype;

/**
 * 京东支付
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午9:49
 */
public class JdPay implements Payment {
    @Override
    public boolean pay(String orderId, long amount) {
        System.out.println("用户选择 京东 支付，订单号为：" + orderId + " ，支付金额：" + amount);
        return true;
    }
}
