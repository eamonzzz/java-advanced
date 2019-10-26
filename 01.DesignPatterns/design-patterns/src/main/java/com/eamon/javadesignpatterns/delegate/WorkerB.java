package com.eamon.javadesignpatterns.delegate;

/**
 * 工人B 他刷漆刷的很好
 *
 * @author EamonZzz
 * @date 2019-10-26 15:13
 */
public class WorkerB implements Worker {
    @Override
    public void doWork(String command) {
        System.out.println("我是工人B，包工头叫我 " + command);
    }
}
