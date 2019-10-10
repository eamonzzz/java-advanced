package com.eamon.javadesignpatterns.proxy.dynamic.jdk.mimeproxy;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author eamon.zhang
 * @date 2019-10-10 下午2:47
 */
public class MimeClassLoader extends ClassLoader {
    private Object target;

    public MimeClassLoader(Object target) {
        this.target = target;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classname = target.getClass().getPackage().getName() + "." + name;
        String filePath = MimeClassLoader.class.getResource("").getPath() + name + ".class";
        try {
            URI uri = new URI("file:///" + filePath);
            Path path = Paths.get(uri);
            File file = path.toFile();
            if (file.exists()) {
                byte[] fileBytes = Files.readAllBytes(path);
                return defineClass(classname, fileBytes, 0, fileBytes.length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
