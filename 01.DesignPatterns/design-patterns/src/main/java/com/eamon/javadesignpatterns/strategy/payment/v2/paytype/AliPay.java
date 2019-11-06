package com.eamon.javadesignpatterns.strategy.payment.v2.paytype;

/**
 * 支付宝支付
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午9:48
 */
public class AliPay implements Payment {
    @Override
    public boolean pay(String orderId, long amount) {
        System.out.println("用户选择 支付宝 支付，订单号为：" + orderId + " ，支付金额：" + amount);
        return true;
    }
}
