package com.eamon.javadesignpatterns.strategy.payment.v1;

/**
 * 订单类，拥有一个支付方法
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午9:18
 */
public class Order {
    // 订单id
    private String orderId;
    // 支付方式
    private String payType;
    // 订单金额
    private long amount;

    public Order(String orderId, String payType, long amount) {
        this.orderId = orderId;
        this.payType = payType;
        this.amount = amount;
    }

    /**
     * 订单支付方法
     *
     * @return
     */
    public boolean pay() {
        // 是否支付成功
        boolean payment = false;
        if ("aliPay".equals(payType)) {
            System.out.println("用户选择 支付宝 支付，订单号为：" + orderId + " ，支付金额：" + amount);
            payment = true;
        } else if ("unionPay".equals(payType)) {
            System.out.println("用户选择 银联 支付，订单号为：" + orderId + " ，支付金额：" + amount);
            payment = true;
        } else if ("jdPay".equals(payType)) {
            System.out.println("用户选择 京东 支付，订单号为：" + orderId + " ，支付金额：" + amount);
            payment = true;
        } else if ("wechatPay".equals(payType)) {
            System.out.println("用户选择 微信 支付，订单号为：" + orderId + " ，支付金额：" + amount);
            payment = true;
        }

        return payment;
    }

}
