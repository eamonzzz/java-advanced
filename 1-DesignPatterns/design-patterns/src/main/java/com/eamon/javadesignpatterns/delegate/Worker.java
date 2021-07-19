package com.eamon.javadesignpatterns.delegate;

/**
 * 抽象 工人
 *
 * @author EamonZzz
 * @date 2019-10-26 15:09
 */
public interface Worker {
    /**
     * 干活
     *
     * @param command 听命令干活
     */
    void doWork(String command);
}
