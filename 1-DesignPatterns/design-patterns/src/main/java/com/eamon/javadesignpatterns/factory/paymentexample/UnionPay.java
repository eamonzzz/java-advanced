package com.eamon.javadesignpatterns.factory.paymentexample;

public class UnionPay implements IPay {
    @Override
    public void pay() {
        System.out.println("Union Pay");
    }
}
