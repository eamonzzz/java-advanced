package com.eamon.javadesignpatterns.proxy.staticproxy;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午3:56
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("实际真实的业务逻辑！");
    }
}
