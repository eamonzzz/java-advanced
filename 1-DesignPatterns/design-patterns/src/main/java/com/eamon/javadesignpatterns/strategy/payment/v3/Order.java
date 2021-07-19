package com.eamon.javadesignpatterns.strategy.payment.v3;

import com.eamon.javadesignpatterns.strategy.payment.v3.paytype.Payment;

/**
 * 订单类，相当于 策略上下文角色
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午9:43
 */
public class Order {
    // 订单id
    private String orderId;
    // 金额
    private long amount;


    public Order(String orderId, long amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    /**
     * 订单支付方法
     *
     * @return
     */
    public boolean pay(String payType) {
        boolean paySuccess;
        Payment payment = PayStrategyFactory.getPayment(payType);
        // 调用支付接口
        paySuccess = payment.pay(orderId, amount);

        if (!paySuccess) {
            // 支付失败逻辑
            System.out.println("支付失败！");
        }
        return paySuccess;
    }
}
