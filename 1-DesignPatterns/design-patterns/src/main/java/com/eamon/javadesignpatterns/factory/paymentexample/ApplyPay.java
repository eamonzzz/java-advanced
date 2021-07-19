package com.eamon.javadesignpatterns.factory.paymentexample;

public class ApplyPay implements IPay {
    @Override
    public void pay() {
        System.out.println("Pay Pal Pay");
    }
}
