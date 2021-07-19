package com.eamon.javadesignpatterns.factory.paymentexample;

public class ApplePalFactory extends AbroadAbstractPaymentFactory {

    @Override
    IPay createPayment() {
        super.init();
        return new ApplyPay();
    }
}
