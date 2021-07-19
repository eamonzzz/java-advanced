package com.eamon.javadesignpatterns.delegate;

import java.util.HashMap;
import java.util.Map;

/**
 * 包工头(也是一名工人)，承接项目，分配工人
 *
 * @author EamonZzz
 * @date 2019-10-26 15:07
 */
public class Contractor implements Worker {

    private Map<String,Worker> targets = new HashMap<String, Worker>();

    public Contractor() {
        targets.put("盖楼", new WorkerA());
        targets.put("刷漆", new WorkerB());
    }

    /**
     * 包工头不需要自己干活
     * @param command
     */
    @Override
    public void doWork(String command) {
        targets.get(command).doWork(command);
    }
}
