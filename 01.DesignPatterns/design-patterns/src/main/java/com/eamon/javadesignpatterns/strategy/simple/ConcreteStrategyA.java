package com.eamon.javadesignpatterns.strategy.simple;

/**
 * 策略具体实现类A
 *
 * @author EamonZzz
 * @date 2019-11-02 16:48
 */
public class ConcreteStrategyA implements IStrategy {

    /**
     * 具体的算法体现
     */
    @Override
    public void algorithmMethod() {
        System.out.println("this is ConcreteStrategyA method...");
    }
}
