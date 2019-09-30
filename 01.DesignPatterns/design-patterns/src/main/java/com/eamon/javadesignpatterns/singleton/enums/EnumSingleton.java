package com.eamon.javadesignpatterns.singleton.enums;

/**
 * @author eamon.zhang
 * @date 2019-09-30 下午3:42
 */
public enum EnumSingleton {
    INSTANCE;

    private Object instance;

    EnumSingleton() {
        instance = new EnumResource();
    }

    public Object getInstance() {
        return instance;
    }

}
