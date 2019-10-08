package com.eamon.javadesignpatterns.singleton;

import com.eamon.javadesignpatterns.singleton.enums.EnumSingleton;
import com.eamon.javadesignpatterns.singleton.lazy.inner.LazyInnerClassSingleton;
import com.eamon.javadesignpatterns.singleton.lazy.inner.LazyInnerClassSingletonPro;
import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * @author eamon.zhang
 * @date 2019-10-08 下午3:05
 */
public class ReflexTest {

    @Test
    public void testReflex() {
        try {
            // 很无聊的情况下，进行破坏
            Class<LazyInnerClassSingletonPro> clazz = LazyInnerClassSingletonPro.class;
            // 通过反射拿到私有的构造方法
            Constructor<LazyInnerClassSingletonPro> c = clazz.getDeclaredConstructor(null);
            // 设置访问属性，强制访问
            c.setAccessible(true);

            // 暴力初始化两次，这就相当于调用了两次构造方法
            LazyInnerClassSingletonPro o1 = c.newInstance();
            LazyInnerClassSingletonPro o2 = c.newInstance();
            // 只要 o1和o2 地址不相等，就可以说明这是两个不同的对象，也就是违背了单例模式的初衷
            System.out.println(o1 == o2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testEnum() {
        try {
            // 很无聊的情况下，进行破坏
            Class<EnumSingleton> clazz = EnumSingleton.class;
            // 通过反射拿到私有的构造方法
            Constructor<EnumSingleton> c = clazz.getDeclaredConstructor(null);
            // 设置访问属性，强制访问
            c.setAccessible(true);

            // 暴力初始化两次，这就相当于调用了两次构造方法
            EnumSingleton o1 = c.newInstance();
            EnumSingleton o2 = c.newInstance();
            // 只要 o1和o2 地址不相等，就可以说明这是两个不同的对象，也就是违背了单例模式的初衷
            System.out.println(o1 == o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEnum1() {
        try {
            Class clazz = EnumSingleton.class;
            Constructor c = clazz.getDeclaredConstructor(String.class, int.class);
            c.setAccessible(true);
            EnumSingleton enumSingleton = (EnumSingleton) c.newInstance("Eamon", 666);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
