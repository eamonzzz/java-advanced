package com.eamon.javadesignpatterns.factory.paymentexample;

public class WeChatPay implements IPay {
    @Override
    public void pay() {
        System.out.println("WeChat Pay");
    }
}
