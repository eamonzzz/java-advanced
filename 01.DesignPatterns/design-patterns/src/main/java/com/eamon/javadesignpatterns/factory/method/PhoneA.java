package com.eamon.javadesignpatterns.factory.method;

import com.eamon.javadesignpatterns.factory.method.Phone;

/**
 * @author eamon.zhang
 * @date 2019-09-27 上午11:02
 */
public class PhoneA implements Phone {
    @Override
    public void type() {
        System.out.println("型号为A的手机！");
    }
}
