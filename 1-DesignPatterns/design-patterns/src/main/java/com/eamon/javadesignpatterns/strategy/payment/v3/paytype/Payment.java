package com.eamon.javadesignpatterns.strategy.payment.v3.paytype;

/**
 * 统一支付接口
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午9:44
 */
public interface Payment {
    boolean pay(String orderId, long amount);
}
