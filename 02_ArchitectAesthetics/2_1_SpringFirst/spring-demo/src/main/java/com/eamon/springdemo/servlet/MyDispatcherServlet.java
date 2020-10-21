package com.eamon.springdemo.servlet;

import com.eamon.springdemo.annontation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:19
 */
public class MyDispatcherServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MyDispatcherServlet.class);

    private Properties contextProperties = new Properties();
    private List<String> classNames = new ArrayList<>();
    private Map<String, Object> ioc = new ConcurrentHashMap<>();
    private Map<String, Method> handlerMapping = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatcher(req, resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        LOG.info("req");
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String url = requestURI.replaceAll(contextPath, "").replaceAll("/+", "/");
        if (!handlerMapping.containsKey(url)) {
            resp.getWriter().write("404 Not Found!!!");
            return;
        }
        Map<String, String[]> parameterMap = req.getParameterMap();

        Method method = handlerMapping.get(url);


        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameterValues = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType == HttpServletRequest.class) {
                parameterValues[i] = req;
            } else if (parameterType == HttpServletResponse.class) {
                parameterValues[i] = resp;
            } else if (parameterType == String.class) {
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (Annotation[] parameterAnnotation : parameterAnnotations) {
                    for (Annotation annotation : parameterAnnotation) {
                        if (annotation instanceof MyRequestParam) {
                            String paramName = ((MyRequestParam) annotation).value();
                            if (!"".equals(paramName.trim())) {
                                String value = Arrays.toString(parameterMap.get(paramName))
                                        .replaceAll("\\[|\\]", "")
                                        .replaceAll("\\s+", ",");
                                parameterValues[i] = value;
                            }
                        }
                    }
                }
            }
        }
        String beanName = toLowerCaseFirst(method.getDeclaringClass().getSimpleName());
        Object invoke = method.invoke(ioc.get(beanName), parameterValues);
        resp.getWriter().write(invoke.toString());

    }

    @Override
    public void init(ServletConfig config) {
        String initParameter = config.getInitParameter("contextConfigLocation");
        // 1. 加载配置文件
        doLoadConfig(initParameter);
        // 2. 扫描相关的类
        String scanPackage = contextProperties.getProperty("scanPackage");
        doScanner(scanPackage);
        // 3. 初始化IoC容器，将扫描到的相关的类实例化，保存到IoC容器中(IoC)
        doInitContainer();
        // 4. AOP
        // todo
        // 5. 完成依赖注入(DI)
        doAutoWired();
        // 6. 初始化HandlerMapping(MVC)
        doInitHandlerMapping();
        LOG.info("My Spring has been init...");
    }


    private void doLoadConfig(String contextConfigLocation) {
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
            contextProperties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        //assert resource != null;
        File classPath = new File(url.getFile());
        File[] files = classPath.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    doScanner(scanPackage + "." + file.getName());
                } else {
                    if (file.getName().endsWith(".class")) {
                        classNames.add(scanPackage + "." + file.getName().replace(".class", ""));
                    }
                }
            }
        }

    }

    private void doInitContainer() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(MyController.class)) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    String beanName = toLowerCaseFirst(clazz.getSimpleName());
                    ioc.put(beanName, instance);
                } else if (clazz.isAnnotationPresent(MyService.class)) {
                    // 1. 先判断是否已经自己起了bean的名字 即  @MyService("beanName")
                    String beanName = clazz.getAnnotation(MyService.class).value();
                    if ("".equals(beanName.trim())) {
                        beanName = toLowerCaseFirst(clazz.getSimpleName());
                    }
                    // 2. 默认将首字母小写
                    beanName = toLowerCaseFirst(beanName);

                    // 3. 装载到ioc容器中
                    if (ioc.containsKey(beanName)) {
                        throw new RuntimeException();
                    }
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    ioc.put(beanName, instance);

                    // 4. 获取这个类实现了哪些接口，并将这些接口也添加进ioc容器中
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        if (ioc.containsKey(anInterface.getSimpleName())) {
                            throw new RuntimeException();
                        }
                        ioc.put(anInterface.getName(), instance);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void doAutoWired() {
        for (Map.Entry<String, Object> objectEntry : ioc.entrySet()) {
            Field[] declaredFields = objectEntry.getValue().getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!declaredField.isAnnotationPresent(MyAutowired.class)) {
                    continue;
                }
                // 如果 MyAutowired 设置了bean的值，就使用这个值，否则使用被注解的属性的类型名称
                MyAutowired myAutowired = declaredField.getAnnotation(MyAutowired.class);
                String beanName = myAutowired.value();
                if ("".equals(beanName)) {
                    beanName = toLowerCaseFirst(declaredField.getType().getName());
                }
                // 强制访问，避免非public修饰的变量访问不了的情况
                declaredField.setAccessible(true);
                try {
                    declaredField.set(objectEntry.getValue(),
                            ioc.get(beanName) // 接口的实现
                    );
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doInitHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(MyController.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                baseUrl = clazz.getAnnotation(MyRequestMapping.class).value();
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
                    String requestMappingValue = requestMapping.value();
                    String url = ("/" + baseUrl + "/" + requestMappingValue).replaceAll("/+", "/");
                    handlerMapping.put(url, method);
                }
            }
        }

    }

    /**
     * 首字母转换为小写
     *
     * @param simpleName 待转换字符串
     * @return 转换后的值
     */
    private String toLowerCaseFirst(String simpleName) {
        char[] charArray = simpleName.toCharArray();
        char firstChar = charArray[0];
        char lowerUpperCaseRange = 65;
        char higherUpperCaseRange = 90;
        if (firstChar >= lowerUpperCaseRange && firstChar <= higherUpperCaseRange) {
            charArray[0] += 32;
        }
        return String.valueOf(charArray);
    }
}
