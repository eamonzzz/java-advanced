package com.eamon.javadesignpatterns.proxy.dynamic.jdk;

import com.eamon.javadesignpatterns.proxy.dynamic.jdk.mimeproxy.MimeClassLoader;
import com.eamon.javadesignpatterns.proxy.dynamic.jdk.mimeproxy.MimeInvocationHandler;
import com.eamon.javadesignpatterns.proxy.dynamic.jdk.mimeproxy.MimeProxy;

import java.lang.reflect.Method;

/**
 * @author eamon.zhang
 * @date 2019-10-10 下午4:25
 */
public class RealSubjectMimeDynamicProxy implements MimeInvocationHandler {
    private Object target;

    public RealSubjectMimeDynamicProxy(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        Class<?> clazz = target.getClass();
        return MimeProxy.newProxyInstance(new MimeClassLoader(target), clazz.getInterfaces(), this);
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
