package com.eamon.javadesignpatterns.singleton.hungry;

import com.eamon.javadesignpatterns.util.ConcurrentExecutor;
import org.junit.Test;

/**
 * @author eamon.zhang
 * @date 2019-09-30 上午11:17
 */
public class HungrySingletonTest {
    @Test
    public void test() {
        try {
            ConcurrentExecutor.execute(() -> {
                HungrySingleton instance = HungrySingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + instance);
            }, 10, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}