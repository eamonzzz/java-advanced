package com.eamon.javadesignpatterns.singleton.hungry;

/**
 * @author eamon.zhang
 * @date 2019-09-30 上午10:46
 */
public class HungryStaticSingleton {
    // 1. 私有化构造器
    private HungryStaticSingleton(){}

    // 2. 实例变量
    private static final HungryStaticSingleton instance;

    // 3. 在静态代码块中实例化
    static {
        instance = new HungryStaticSingleton();
    }

    // 4. 提供获取实例方法
    public static HungryStaticSingleton getInstance(){
        return instance;
    }
}
