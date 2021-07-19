package com.eamon.javadesignpatterns.proxy.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:23
 */
public class RealSubjectCglibDynamicProxy implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) {
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 要把哪个设置为即将生成的新类父类
        enhancer.setSuperclass(clazz);
        // 设置回调对象
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object invokeSuper = proxy.invokeSuper(obj, args);
        after();
        return invokeSuper;
    }

    private void before() {
        System.out.println("前置增强！");
    }

    private void after() {
        System.out.println("后置增强！");
    }
}
