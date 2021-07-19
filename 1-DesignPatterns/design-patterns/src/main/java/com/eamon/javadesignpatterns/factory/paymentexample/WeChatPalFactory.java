package com.eamon.javadesignpatterns.factory.paymentexample;

public class WeChatPalFactory extends ChinaAbstractPaymentFactory {

    @Override
    IPay createPayment() {
        super.init();
        return new WeChatPay();
    }
}
