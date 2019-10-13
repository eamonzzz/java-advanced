package com.eamon.javadesignpatterns.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:08
 */
public class RealSubjectJDKDynamicProxy implements InvocationHandler {
    // 被代理对象的引用
    private Object target;
    // 通过构造器传入对象引用
    public RealSubjectJDKDynamicProxy(Object target) {
        this.target = target;
    }
    // 获得 JDK 动态代理创建的代理对象
    public Object getInstance() {
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        before();
        // 代理执行被代理对象的相应方法
        Object invoke = method.invoke(target, objects);
        after();
        return invoke;
    }

    private void before() {
        System.out.println("前置增强！");
    }

    private void after() {
        System.out.println("后置增强！");
    }
}
