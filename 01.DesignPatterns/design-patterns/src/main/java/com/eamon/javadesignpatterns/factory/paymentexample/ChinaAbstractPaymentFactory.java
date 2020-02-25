package com.eamon.javadesignpatterns.factory.paymentexample;

public abstract class ChinaAbstractPaymentFactory extends AbstractPaymentFactory {

    @Override
    public void init() {
        super.init();
        System.out.println("国内支付其他初始化数据");
    }

    abstract IPay createPayment();
}
