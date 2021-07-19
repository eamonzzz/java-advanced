package com.eamon.javadesignpatterns.strategy.simple;

/**
 * 策策略上下文，负责和具体的策略实现交互，通常策略上下文对象会持有一个真正的策略实现对象，
 * 策略上下文还可以让具体的策略实现从其中获取相关数据，回调策略上下文对象的方法。
 *
 * @author EamonZzz
 * @date 2019-11-02 16:11
 */
public class StrategyContext {
    /**
     * 策略实现的引用
     */
    private IStrategy strategy;

    /**
     * 构造器注入具体的策略类
     *
     * @param iStrategy 策略实现的引用
     */
    public StrategyContext(IStrategy iStrategy) {
        this.strategy = iStrategy;
    }

    public void contextMethod() {
        // 调用策略实现的方法
        strategy.algorithmMethod();
    }
}
