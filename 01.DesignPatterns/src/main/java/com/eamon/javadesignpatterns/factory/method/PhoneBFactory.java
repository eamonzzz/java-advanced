package com.eamon.javadesignpatterns.factory.method;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午1:50
 */
public class PhoneBFactory implements PhoneFactory {
    @Override
    public Phone product() {
        return new PhoneB();
    }
}
