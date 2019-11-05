package com.eamon.javadesignpatterns.strategy.simple;

/**
 * 策略类抽象接口，具体策略实现由其子类来实现
 *
 * @author EamonZzz
 * @date 2019-11-02 16:12
 */
public interface IStrategy {

    /**
     * 定义的抽象算法方法 来约束具体的算法实现方法
     */
    void algorithmMethod();
}
