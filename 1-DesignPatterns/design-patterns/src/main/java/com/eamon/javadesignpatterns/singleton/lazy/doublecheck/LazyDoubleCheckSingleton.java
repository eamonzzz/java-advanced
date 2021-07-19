package com.eamon.javadesignpatterns.singleton.lazy.doublecheck;

/**
 * @author eamon.zhang
 * @date 2019-09-30 下午2:03
 */
public class LazyDoubleCheckSingleton {
    private LazyDoubleCheckSingleton() {
    }

    private static volatile LazyDoubleCheckSingleton instance = null;

    public static LazyDoubleCheckSingleton getInstance() {
        // 这里判断是为了过滤不必要的同步加锁，因为如果已经实例化了，就可以直接返回了
        if (instance == null) {
            // 如果未初始化，则对资源进行上锁保护，待实例化完成之后进行释放（注意，可能多个线程会同时进入）
            synchronized (LazyDoubleCheckSingleton.class) {
                // 这里的if作用是：如果后面的进程在前面一个线程实例化完成之后拿到锁，进入这个代码块，
                // 显然，资源已经被实例化过了，所以需要进行判断过滤
                if (instance == null) {
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
