package com.eamon.javadesignpatterns.factory.paymentexample;

public abstract class AbstractPaymentFactory {
    public void init(){
        System.out.println("支付数据初始化");
    }
}
