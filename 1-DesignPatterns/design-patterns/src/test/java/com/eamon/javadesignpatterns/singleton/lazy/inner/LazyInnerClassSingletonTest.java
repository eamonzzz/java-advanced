package com.eamon.javadesignpatterns.singleton.lazy.inner;

import com.eamon.javadesignpatterns.util.ConcurrentExecutor;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-09-30 下午3:04
 */
public class LazyInnerClassSingletonTest {

    @Test
    public void test(){
        try {
            ConcurrentExecutor.execute(()->{
                LazyInnerClassSingleton instance = LazyInnerClassSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + instance);
            },10,10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReflect(){
        try {
            // 很无聊的情况下，进行破坏
            Class<?> clazz = LazyInnerClassSingleton.class;
            // 通过反射获取私有的构造方法
            Constructor constructor = clazz.getDeclaredConstructor(null);
            // 设置访问权限为true，强行访问
            constructor.setAccessible(true);
            //暴力初始化
            Object o1 = constructor.newInstance();
            // 再暴力初始化一次
            Object o2 = constructor.newInstance();
            // 如果 o1 和 o2 是同一对象，则应该返回true
            System.out.println(o1 == o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}