package com.eamon.javadesignpatterns.singleton.lazy.inner;

import java.io.Serializable;

/**
 * @author eamon.zhang
 * @date 2019-09-30 下午2:55
 */
public class LazyInnerClassSingletonPro implements Serializable {

    private LazyInnerClassSingletonPro() {
        if (LazyHolder.INSTANCE != null) {
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    // 注意关键字final，保证方法不被重写和重载
    public static final LazyInnerClassSingletonPro getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        // 注意 final 关键字（保证不被修改）
        private static final LazyInnerClassSingletonPro INSTANCE = new LazyInnerClassSingletonPro();
    }

    // 解决反序列化对象不一致问题
    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }
}
