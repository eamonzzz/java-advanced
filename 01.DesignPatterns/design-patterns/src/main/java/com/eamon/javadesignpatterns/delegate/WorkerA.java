package com.eamon.javadesignpatterns.delegate;

/**
 * 工人A 他砌砖砌的很好，所以叫来盖楼比较好
 *
 * @author EamonZzz
 * @date 2019-10-26 15:11
 */
public class WorkerA implements Worker {
    @Override
    public void doWork(String command) {
        System.out.println("我是工人A，包工头叫我 " + command);
    }
}
