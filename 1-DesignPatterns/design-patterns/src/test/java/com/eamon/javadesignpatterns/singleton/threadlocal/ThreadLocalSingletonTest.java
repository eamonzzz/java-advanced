package com.eamon.javadesignpatterns.singleton.threadlocal;

import com.eamon.javadesignpatterns.util.ConcurrentExecutor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author EamonZzz
 * @date 2019-10-06 21:44
 */
public class ThreadLocalSingletonTest {

    @Test
    public void test() {
        System.out.println("-------------- 单线程 start ---------");
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println("-------------- 单线程 end ---------");
        System.out.println("-------------- 多线程 start ---------");
        try {
            ConcurrentExecutor.execute(() -> {
                ThreadLocalSingleton singleton = ThreadLocalSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + singleton);

            }, 5, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------------- 多线程 end ---------");
    }

    public static void main(String[] args) {
        System.out.println("-------------- 单线程 start ---------");
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println("-------------- 单线程 end ---------");
        System.out.println("-------------- 多线程 start ---------");
        try {
            ConcurrentExecutor.execute(() -> {
                ThreadLocalSingleton singleton = ThreadLocalSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + singleton);

            }, 5, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------------- 多线程 end ---------");
    }

}