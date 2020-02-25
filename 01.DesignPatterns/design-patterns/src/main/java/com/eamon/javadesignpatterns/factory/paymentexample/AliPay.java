package com.eamon.javadesignpatterns.factory.paymentexample;

public class AliPay implements IPay {

    @Override
    public void pay() {
        System.out.println("AliPay");
    }
}
