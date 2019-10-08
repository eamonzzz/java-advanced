package com.eamon.javadesignpatterns.singleton;

import com.eamon.javadesignpatterns.singleton.lazy.inner.LazyInnerClassSingleton;
import com.eamon.javadesignpatterns.singleton.lazy.inner.LazyInnerClassSingletonPro;

import java.io.*;

/**
 * @author eamon.zhang
 * @date 2019-10-08 下午3:06
 */
public class SerializableTest {
    public static void main(String[] args) {
        LazyInnerClassSingletonPro s1 = null;
        LazyInnerClassSingletonPro s2 = LazyInnerClassSingletonPro.getInstance();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("LazyInnerClassSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("LazyInnerClassSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (LazyInnerClassSingletonPro)ois.readObject();
            ois.close();

            System.out.println(s1);
            System.out.println(s2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
