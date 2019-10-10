package com.eamon.javadesignpatterns.proxy.dynamic.jdk.mimeproxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eamon.zhang
 * @date 2019-10-10 下午3:08
 */
public class MimeProxy {
    private static final String ln = "\r\n";
    private static final String semi = ";";

    private static Map<Class, Class> mappings = new HashMap<Class, Class>();

    static {
        mappings.put(int.class, Integer.class);
    }

    public static Object newProxyInstance(MimeClassLoader loader, Class<?>[] interfaces, MimeInvocationHandler h)
            throws IllegalArgumentException {
        try {
            // 1. 动态生成 .java 文件
            String src = generateSrc(interfaces);
//            System.out.println(src);
            // 2. java 文件输出到磁盘
            String filePath = MimeProxy.class.getResource("").getPath();
//            System.out.println(filePath);
            File f = new File(filePath + "$Proxy8.java");
//            f.deleteOnExit();
            FileWriter fw = new FileWriter(f);
            fw.write(src);
            fw.flush();
            fw.close();
            // 3. 把 java 文件编译成 .class 文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> iterable = sjfm.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null, null, null, iterable);
            task.call();
            sjfm.close();
            // 4. 把.class 文件加载到jvm
            Class<?> proxyClass = loader.findClass("$Proxy8");
            Constructor<?> c = proxyClass.getConstructor(MimeInvocationHandler.class);
            f.delete();

            // 5. 返回字节码重组以后的新的代理对象
            return c.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static String generateSrc(Class<?>[] interfaces) {
        // 这里使用 StringBuffer 线程安全
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(interfaces[0].getPackage().getName()).append(semi).append(ln);
        sb.append("import ").append(interfaces[0].getName()).append(semi).append(ln);
        sb.append("import java.lang.reflect.*;").append(ln);
        sb.append("import ").append(interfaces[0].getPackage().getName()).append(".mimeproxy.MimeInvocationHandler;").append(ln);
        sb.append("public class $Proxy8 implements ").append(interfaces[0].getSimpleName()).append(" {").append(ln);
        sb.append("MimeInvocationHandler h;" + ln);
        sb.append("public $Proxy8(MimeInvocationHandler h) {").append(ln);
        sb.append("this.h = h;").append(ln);
        sb.append("}").append(ln);

        for (Method method : interfaces[0].getMethods()) {
            Class<?>[] params = method.getParameterTypes();

            StringBuffer paramNames = new StringBuffer();
            StringBuffer paramValues = new StringBuffer();
            StringBuffer paramClasses = new StringBuffer();

            for (Class<?> clazz : params) {
                String type = clazz.getName();
                String paramName = toLowerFirstCase(clazz.getSimpleName());

                paramNames.append(type).append(" ").append(paramName);

                paramValues.append(paramName);
                paramClasses.append(clazz.getName()).append(".class");

                for (int i = 0; i < params.length; i++) {
                    paramNames.append(",");
                    paramValues.append(",");
                    paramClasses.append(",");
                }
            }

            sb.append("public ").append(method.getReturnType().getName()).append(" ").append(method.getName())
                    .append("(").append(paramNames.toString()).append(") {").append(ln);
            sb.append("try {").append(ln);
            // Method m = interfaces[0].getName().class.getMethod(method.getName()),new Class[]{paramClasses.toString()});
            sb.append("Method m = ").append(interfaces[0].getName()).append(".class.getMethod(\"")
                    .append(method.getName()).append("\", new Class[]{").append(paramClasses.toString()).append("});")
                    .append(ln);
            // return this.h.invoke(this, m, new Object[]{paramValues}, method.getReturnType());
            sb.append(hasReturnValue(method.getReturnType()) ? "return " : "")
                    .append(getCaseCode("this.h.invoke(this,m,new Object[]{" + paramValues + "})", method.getReturnType()))
                    .append(";")
                    .append(ln);
            sb.append("} catch (Error _ex) {}").append(ln);
            sb.append("catch (Throwable e) {").append(ln);
            sb.append("throw new UndeclaredThrowableException(e);").append(ln);
            sb.append("}");
            sb.append(getReturnEmptyCode(method.getReturnType())).append(ln);
            sb.append("}");

        }
        sb.append("}").append(ln);

        return sb.toString();
    }

    private static String getReturnEmptyCode(Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "return 0;";
        } else if (returnClass == void.class) {
            return "";
        } else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code, Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "((" + mappings.get(returnClass).getName() + ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz) {
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src) {
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
