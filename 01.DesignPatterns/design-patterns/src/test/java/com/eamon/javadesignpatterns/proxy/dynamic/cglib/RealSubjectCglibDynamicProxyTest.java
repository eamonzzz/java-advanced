package com.eamon.javadesignpatterns.proxy.dynamic.cglib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:30
 */
public class RealSubjectCglibDynamicProxyTest {
    @Test
    public void test(){
        RealSubjectCglibDynamicProxy proxy = new RealSubjectCglibDynamicProxy();
        RealSubject instance = (RealSubject) proxy.getInstance(RealSubject.class);
        instance.request();
    }

}