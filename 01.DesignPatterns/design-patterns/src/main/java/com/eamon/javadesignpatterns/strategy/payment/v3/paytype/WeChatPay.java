package com.eamon.javadesignpatterns.strategy.payment.v3.paytype;

/**
 * 微信支付
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午9:49
 */
public class WeChatPay implements Payment {
    @Override
    public boolean pay(String orderId, long amount) {
        System.out.println("用户选择 微信 支付，订单号为：" + orderId + " ，支付金额：" + amount);
        return true;
    }
}
