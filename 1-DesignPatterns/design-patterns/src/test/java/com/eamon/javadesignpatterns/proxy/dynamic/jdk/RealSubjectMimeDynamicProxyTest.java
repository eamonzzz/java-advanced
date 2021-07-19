package com.eamon.javadesignpatterns.proxy.dynamic.jdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-10-10 下午4:26
 */
public class RealSubjectMimeDynamicProxyTest {

    @Test
    public void test(){
        RealSubject realSubject = new RealSubject();
        RealSubjectMimeDynamicProxy mimeDynamicProxy = new RealSubjectMimeDynamicProxy(realSubject);
        Subject instance = (Subject) mimeDynamicProxy.getInstance();
        instance.request();
    }

}