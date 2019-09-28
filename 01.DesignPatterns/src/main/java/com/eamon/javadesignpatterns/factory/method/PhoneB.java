package com.eamon.javadesignpatterns.factory.method;

import com.eamon.javadesignpatterns.factory.method.Phone;

/**
 * @author eamon.zhang
 * @date 2019-09-27 上午11:03
 */
public class PhoneB implements Phone {
    @Override
    public void type() {
        System.out.println("型号为B的手机！");
    }
}
