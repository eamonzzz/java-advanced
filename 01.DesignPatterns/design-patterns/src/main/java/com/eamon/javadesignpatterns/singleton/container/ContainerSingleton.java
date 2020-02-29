package com.eamon.javadesignpatterns.singleton.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author EamonZzz
 * @date 2019-10-06 18:28
 */
public class ContainerSingleton {
    private ContainerSingleton() {
    }

    private static Map<String, Object> ioc = new ConcurrentHashMap<String, Object>();

    public static Object getInstance(String className) {
        // 加一层判断，如果已经存在了，就取出返回
        if (ioc.containsKey(className)) {
            return ioc.get(className);
        }
        // 否则，将ioc锁住，进行实例创建
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object object = null;
                try {
                    object = Class.forName(className).newInstance();
                    ioc.put(className, object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return object;
            } else {
                return ioc.get(className);
            }
        }
    }
}
