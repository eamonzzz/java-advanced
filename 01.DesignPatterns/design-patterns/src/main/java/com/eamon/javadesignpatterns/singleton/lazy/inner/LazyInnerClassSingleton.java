package com.eamon.javadesignpatterns.singleton.lazy.inner;

/**
 *
 * @author eamon.zhang
 * @date 2019-09-30 下午2:55
 */
public class LazyInnerClassSingleton {

    private LazyInnerClassSingleton() {
    }

    // 注意关键字final，保证方法不被重写和重载
    public static final LazyInnerClassSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        // 注意 final 关键字（保证不被修改）
        private static final LazyInnerClassSingleton INSTANCE = new LazyInnerClassSingleton();
    }
}
