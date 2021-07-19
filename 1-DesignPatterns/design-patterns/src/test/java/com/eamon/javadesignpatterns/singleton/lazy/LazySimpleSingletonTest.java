package com.eamon.javadesignpatterns.singleton.lazy;

import com.eamon.javadesignpatterns.singleton.lazy.simple.LazySimpleSingleton;
import com.eamon.javadesignpatterns.singleton.lazy.sync.LazySimpleSyncSingleton;
import com.eamon.javadesignpatterns.util.ConcurrentExecutor;
import org.junit.Test;

/**
 * @author eamon.zhang
 * @date 2019-09-30 上午11:12
 */
public class LazySimpleSingletonTest {

    @Test
    public void test() {
        try {
            ConcurrentExecutor.execute(() -> {
                LazySimpleSingleton instance = LazySimpleSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + instance);
            }, 5, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSync(){
        try {
            ConcurrentExecutor.execute(() -> {
                LazySimpleSyncSingleton instance = LazySimpleSyncSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + instance);
            }, 5, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}