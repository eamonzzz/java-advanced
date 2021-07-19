package com.eamon.javadesignpatterns.factory.paymentexample;

public class AliPayFactory extends ChinaAbstractPaymentFactory {
    @Override
    IPay createPayment() {
        super.init();
        return new AliPay();
    }
}
