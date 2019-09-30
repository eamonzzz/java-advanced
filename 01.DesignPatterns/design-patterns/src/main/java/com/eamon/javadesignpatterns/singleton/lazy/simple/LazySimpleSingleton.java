package com.eamon.javadesignpatterns.singleton.lazy.simple;

/**
 * @author eamon.zhang
 * @date 2019-09-30 上午10:55
 */
public class LazySimpleSingleton {
    private LazySimpleSingleton(){}
    private static LazySimpleSingleton instance = null;

    public static LazySimpleSingleton getInstance(){
        if (instance == null) {
            instance = new LazySimpleSingleton();
        }
        return instance;
    }
}
