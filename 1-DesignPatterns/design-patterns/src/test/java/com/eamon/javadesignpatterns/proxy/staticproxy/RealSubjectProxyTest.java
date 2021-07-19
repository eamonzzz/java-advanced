package com.eamon.javadesignpatterns.proxy.staticproxy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午3:59
 */
public class RealSubjectProxyTest {

    @Test
    public void test(){
        // 定义一个具体的目标对象
        RealSubject realSubject = new RealSubject();

        // 定义一个代理对象，将目标对象的引用传入
        RealSubjectProxy proxy = new RealSubjectProxy(realSubject);
        // 执行代理方法
        proxy.request();
    }

}