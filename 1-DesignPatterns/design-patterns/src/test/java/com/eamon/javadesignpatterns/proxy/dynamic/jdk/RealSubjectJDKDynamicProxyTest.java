package com.eamon.javadesignpatterns.proxy.dynamic.jdk;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:13
 */
public class RealSubjectJDKDynamicProxyTest {

    @Test
    public void test(){
        Subject realSubject = new RealSubject();
        RealSubjectJDKDynamicProxy proxy = new RealSubjectJDKDynamicProxy(realSubject);
        Subject instance = (Subject) proxy.getInstance();
        instance.request();
        System.out.println(realSubject.getClass());
        System.out.println(instance.getClass());
    }

    @Test
    public void test1(){
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        RealSubject realSubject = new RealSubject();

        Class<?>[] interfaces = realSubject.getClass().getInterfaces();

        for (Class<?> anInterface : interfaces) {
            String name = anInterface.getPackage().getName();
            System.out.println(name);
            String interfaceName = anInterface.getName();
            System.out.println(interfaceName);
            String simpleName = anInterface.getSimpleName();
            System.out.println(simpleName);
        }

        RealSubjectJDKDynamicProxy proxy = new RealSubjectJDKDynamicProxy(realSubject);
        Subject instance = (Subject) proxy.getInstance();
        try {
            byte[] proxychar=  ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Subject.class});
            OutputStream outputStream = new FileOutputStream("/Users/eamon.zhang/IdeaProjects/own/java-advanced/01.DesignPatterns/design-patterns/"+instance.getClass().getSimpleName()+".class");
            outputStream.write(proxychar);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance.request();
        System.out.println(instance.getClass());

    }

}