package com.eamon.javadesignpatterns.proxy.dynamic.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
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

    @Test
    public void test1(){
        //利用 cglib 的代理类可以将内存中的 class 文件写入本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/eamon.zhang/Documents/cglib");
        RealSubjectCglibDynamicProxy proxy = new RealSubjectCglibDynamicProxy();
        RealSubject instance = (RealSubject) proxy.getInstance(RealSubject.class);
        instance.request();
    }
}