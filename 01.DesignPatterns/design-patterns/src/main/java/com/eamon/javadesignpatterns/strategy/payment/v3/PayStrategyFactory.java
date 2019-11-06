package com.eamon.javadesignpatterns.strategy.payment.v3;

import com.eamon.javadesignpatterns.strategy.payment.v3.paytype.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建支付策略的工厂类
 *
 * @author eamon.zhang
 * @date 2019-11-06 上午10:32
 */
public class PayStrategyFactory {

    // 支付方式常量
    public static final String ALI_PAY = "aliPay";
    public static final String JD_PAY = "jdPay";
    public static final String WECHAT_PAY = "wechatPay";
    public static final String UNION_PAY = "unionPay";

    // 支付方式管理集合
    private static Map<String, Payment> PAYMENT_STRATEGY_MAP = new HashMap<>();

    static {
        PAYMENT_STRATEGY_MAP.put(ALI_PAY, new AliPay());
        PAYMENT_STRATEGY_MAP.put(JD_PAY, new JdPay());
        PAYMENT_STRATEGY_MAP.put(WECHAT_PAY, new WeChatPay());
        PAYMENT_STRATEGY_MAP.put(UNION_PAY, new UnionPay());
    }

    /**
     * 获取支付方式类
     *
     * @param payType 前端传入支付方式
     * @return
     */
    public static Payment getPayment(String payType) {
        Payment payment = PAYMENT_STRATEGY_MAP.get(payType);
        if (payment == null) {
            throw new NullPointerException("支付方式选择错误！");
        }
        return payment;
    }
}
