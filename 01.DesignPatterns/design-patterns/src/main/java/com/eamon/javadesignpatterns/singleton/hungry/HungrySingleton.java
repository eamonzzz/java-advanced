package com.eamon.javadesignpatterns.singleton.hungry;

/**
 * @author eamon.zhang
 * @date 2019-09-30 上午9:26
 */
public class HungrySingleton {
    // 1.私有化构造器
    private HungrySingleton (){}
    // 2.在类的内部创建自行实例
    private static final HungrySingleton instance = new HungrySingleton();
    // 3.提供获取唯一实例的方法（全局访问点）
    public static HungrySingleton getInstance(){
        return instance;
    }
}
