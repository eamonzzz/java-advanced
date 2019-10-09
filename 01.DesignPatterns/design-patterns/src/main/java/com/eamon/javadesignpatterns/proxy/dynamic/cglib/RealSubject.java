package com.eamon.javadesignpatterns.proxy.dynamic.cglib;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:22
 */
public class RealSubject {
    public void request(){
        System.out.println("真实处理逻辑！");
    }
}
