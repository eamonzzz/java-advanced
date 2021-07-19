package com.eamon.javadesignpatterns.singleton.threadlocal;

/**
 * @author EamonZzz
 * @date 2019-10-06 21:40
 */
public class ThreadLocalSingleton {
    private ThreadLocalSingleton() {
    }

    private static final ThreadLocal<ThreadLocalSingleton> instance = ThreadLocal.withInitial(ThreadLocalSingleton::new);

    public static ThreadLocalSingleton getInstance() {
        return instance.get();
    }
}
