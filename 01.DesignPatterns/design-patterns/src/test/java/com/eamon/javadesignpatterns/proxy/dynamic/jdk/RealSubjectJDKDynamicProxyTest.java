package com.eamon.javadesignpatterns.proxy.dynamic.jdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:13
 */
public class RealSubjectJDKDynamicProxyTest {

    @Test
    public void test(){
        RealSubject realSubject = new RealSubject();

        RealSubjectJDKDynamicProxy proxy = new RealSubjectJDKDynamicProxy(realSubject);
        Subject instance = (Subject) proxy.getInstance();
        instance.request();
    }

}