package com.eamon.javadesignpatterns.factory.paymentexample;

import java.lang.reflect.InvocationTargetException;

public abstract class AbroadAbstractPaymentFactory extends AbstractPaymentFactory {

    @Override
    public void init() {
        super.init();
        System.out.println("国外支付其他初始化数据");
    }

    abstract IPay createPayment();

}
