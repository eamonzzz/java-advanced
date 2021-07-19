package com.eamon.javadesignpatterns.factory.paymentexample;

public class UnionPayFactory extends ChinaAbstractPaymentFactory {

    @Override
    IPay createPayment() {
        super.init();
        return new UnionPay();
    }
}
