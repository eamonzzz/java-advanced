package com.eamon.javadesignpatterns.factory.method;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午1:50
 */
public class PhoneAFactory implements PhoneFactory {
    @Override
    public Phone product() {
        return new PhoneA();
    }
}
