package com.eamon.javadesignpatterns.factory.method;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午1:54
 */
public class PhoneFactoryTest {

    @Test
    public void product() {
        PhoneFactory factory = new PhoneAFactory();
        factory.product().type();

        factory = new PhoneBFactory();
        factory.product().type();

    }
}