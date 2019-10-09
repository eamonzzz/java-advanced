package com.eamon.javadesignpatterns.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:08
 */
public class RealSubjectJDKDynamicProxy implements InvocationHandler {
    private Object target;

    public RealSubjectJDKDynamicProxy(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        before();
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
