package com.eamon.javadesignpatterns.singleton.lazy.sync;

/**
 * @author eamon.zhang
 * @date 2019-09-30 上午10:55
 */
public class LazySimpleSyncSingleton {
    private LazySimpleSyncSingleton() {
    }

    private static LazySimpleSyncSingleton instance = null;

    public synchronized static LazySimpleSyncSingleton getInstance() {
        if (instance == null) {
            instance = new LazySimpleSyncSingleton();
        }
        return instance;
    }
}
