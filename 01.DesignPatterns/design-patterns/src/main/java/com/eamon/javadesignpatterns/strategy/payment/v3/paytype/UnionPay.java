package com.eamon.javadesignpatterns.strategy.payment.v3.paytype;

/**
 * 银联支付
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午9:50
 */
public class UnionPay implements Payment {
    @Override
    public boolean pay(String orderId, long amount) {
        System.out.println("用户选择 银联 支付，订单号为：" + orderId + " ，支付金额：" + amount);
        return true;
    }
}
