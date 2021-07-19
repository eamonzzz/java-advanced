package com.eamon.javadesignpatterns.singleton.enums;

import com.eamon.javadesignpatterns.util.ConcurrentExecutor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-09-30 下午3:47
 */
public class EnumSingletonTest {


    @Test
    public void test() {
        try {
            ConcurrentExecutor.execute(() -> {
                EnumSingleton instance = EnumSingleton.INSTANCE;
                System.out.println(instance.getInstance());
            }, 10, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}