package com.eamon.javadesignpatterns.proxy.dynamic.jdk;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:06
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("真实处理逻辑！");
    }
}
