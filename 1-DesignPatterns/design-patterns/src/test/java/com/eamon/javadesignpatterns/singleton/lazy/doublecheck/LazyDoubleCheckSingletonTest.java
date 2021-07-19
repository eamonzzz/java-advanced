package com.eamon.javadesignpatterns.singleton.lazy.doublecheck;

import com.eamon.javadesignpatterns.util.ConcurrentExecutor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-09-30 下午2:26
 */
public class LazyDoubleCheckSingletonTest {

    @Test
    public void test(){
        try {
            ConcurrentExecutor.execute(()->{
                LazyDoubleCheckSingleton instance = LazyDoubleCheckSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + instance);
            },10,10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}