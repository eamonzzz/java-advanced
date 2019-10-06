package com.eamon.javadesignpatterns.singleton.container;

import com.eamon.javadesignpatterns.util.ConcurrentExecutor;
import org.junit.Test;

/**
 * @author EamonZzz
 * @date 2019-10-06 18:36
 */
public class ContainerSingletonTest {

    @Test
    public void test() {
        try {
            ConcurrentExecutor.execute(() -> {
                Object bean = ContainerSingleton
                        .getInstance("com.eamon.javadesignpatterns.singleton.container.Resource");
                System.out.println(bean);
            }, 8, 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}