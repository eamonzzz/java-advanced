package com.eamon.javadesignpatterns.factory.simple;

import com.eamon.javadesignpatterns.factory.simple.PhoneA;
import com.eamon.javadesignpatterns.factory.simple.PhoneB;
import com.eamon.javadesignpatterns.factory.simple.PhoneFactory;
import org.junit.Test;

/**
 * @author eamon.zhang
 * @date 2019-09-27 上午11:09
 */
public class PhoneFactoryTest {

    @Test
    public void product() {
        PhoneFactory phoneFactory = new PhoneFactory();
        phoneFactory.product(PhoneA.class).type();

        phoneFactory.product(PhoneB.class).type();

    }
}