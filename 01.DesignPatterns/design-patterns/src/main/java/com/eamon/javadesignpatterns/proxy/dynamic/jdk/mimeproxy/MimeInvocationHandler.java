package com.eamon.javadesignpatterns.proxy.dynamic.jdk.mimeproxy;

import java.lang.reflect.Method;

/**
 * @author eamon.zhang
 * @date 2019-10-10 下午2:46
 */
public interface MimeInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
