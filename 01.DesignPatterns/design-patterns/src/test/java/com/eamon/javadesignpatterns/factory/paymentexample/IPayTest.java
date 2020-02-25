package com.eamon.javadesignpatterns.factory.paymentexample;

import org.junit.Test;

import static org.junit.Assert.*;

public class IPayTest {

    @Test
    public void test(){
        AbroadAbstractPaymentFactory abroadPayFactory = new ApplePalFactory();
        ChinaAbstractPaymentFactory chinaFactory = new AliPayFactory();

        abroadPayFactory.createPayment().pay();

        chinaFactory.createPayment().pay();

    }

}