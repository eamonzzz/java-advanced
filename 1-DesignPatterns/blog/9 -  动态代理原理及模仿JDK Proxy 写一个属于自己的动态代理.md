**本篇文章代码内容较多，讲的可能会有些粗糙，大家可以选择性阅读。**

> 本篇文章的目的是简单的分析动态代理的原理及模仿`JDK Proxy`手写一个动态代理以及对几种代理做一个总结。

对于代理模式的介绍和讲解，网上已经有很多优质的文章，我这里就不会再过多的介绍了，这里推荐几篇优质的文章作为参考：

1. [给女朋友讲解什么是代理模式](https://juejin.im/post/5af0335c6fb9a07ace58cc8f#comment)
2. [轻松学，Java 中的代理模式及动态代理](https://blog.csdn.net/briblue/article/details/73928350)

另外，我的 github 仓库对应目录中也有相关的基础示例代码：[https://github.com/eamonzzz/java-advanced...](https://github.com/eamonzzz/java-advanced/tree/master/01.DesignPatterns/design-patterns/src/main/java/com/eamon/javadesignpatterns/proxy)

# JDK Proxy 动态代理

动态代理的概念这里就不再阐述了；动态代理相对于静态代理来说，它的功能更加强大，随着业务的扩展，适应性更强。

在说动态代理原理之前，我们还是来看看动态代理的一般使用。

## 使用

本篇文章的使用示例，是以一个最为简单的代理模式的代码为例，相信大家在学习或了解代理模式的时候都有看到或者接触过这些代码。

1. 先创建一个`Subject`主体抽象接口：

```java
/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:06
 */
public interface Subject {
    void request();
}
```

2. 再创建一个真实的主体`RealSubject`来处理我们的真实的逻辑：

```java
/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:06
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("真实处理逻辑！");
    }
}
```

3. 再不修改`RealSubject`类的情况下，如果我们要实现在执行`RealSubject`类中`request()`方法之前或之后执行一段逻辑的话，该怎么实现呢？这就得创建一个代理类，来达到增强原有代码的目的。所以现在创建一个 JDK 动态代理类 `RealSubjectJDKDynamicProxy` ：

```java
/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:08
 */
public class RealSubjectJDKDynamicProxy implements InvocationHandler {
    // 被代理对象的引用
    private Object target;
    // 通过构造器传入对象引用
    public RealSubjectJDKDynamicProxy(Object target) {
        this.target = target;
    }
    // 获得 JDK 动态代理创建的代理对象
    public Object getInstance() {
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        before();
        // 代理执行被代理对象的相应方法
        Object invoke = method.invoke(target, objects);
        after();
        return invoke;
    }

    private void before() {
        System.out.println("前置增强！");
    }

    private void after() {
        System.out.println("后置增强！");
    }
}
```

4. 测试代码：

```java
@Test
public void test(){
    Subject realSubject = new RealSubject();
    RealSubjectJDKDynamicProxy proxy = new RealSubjectJDKDynamicProxy(realSubject);
    Subject instance = (Subject) proxy.getInstance();
    instance.request();
    System.out.println(realSubject.getClass());
    System.out.println(instance.getClass());
}
```

5. 测试结果

```
前置增强！
真实处理逻辑！
后置增强！
class com.eamon.javadesignpatterns.proxy.dynamic.jdk.RealSubject
class com.sun.proxy.$Proxy8
```

从结果来看，上面的代码已经达到了我们的**增强**的目的。

## 原理分析

不知道大家有没有注意到上面的测试代码中，最后两行我将代理之前和代理之后的`class`对象给打印了出来；并且发现，这两个对象并非同一个，最重要的是，经过代理之后的对象的`Subject`是`com.sun.proxy.$Proxy8`而不是`com.eamon.javadesignpatterns.proxy.dynamic.jdk.RealSubject`或者`com.eamon.javadesignpatterns.proxy.dynamic.jdk.Subject`，那么这个`instance`到底是从哪里来？带着这个疑问，我们来通过 JDK Proxy 源码来分析一下：

我们跟进`RealSubjectJDKDynamicProxy`类中的`Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);`方法：

```java
public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
    throws IllegalArgumentException
{
    Objects.requireNonNull(h);

    final Class<?>[] intfs = interfaces.clone();
    final SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
        checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
    }

    /*
     * Look up or generate the designated proxy class.
     */
    Class<?> cl = getProxyClass0(loader, intfs);

    /*
     * Invoke its constructor with the designated invocation handler.
     */
    try {
        ...
        final Constructor<?> cons = cl.getConstructor(constructorParams);
        final InvocationHandler ih = h;
        if (!Modifier.isPublic(cl.getModifiers())) {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    cons.setAccessible(true);
                    return null;
                }
            });
        }
        return cons.newInstance(new Object[]{h});
    } catch (IllegalAccessException|InstantiationException e) {
        throw new InternalError(e.toString(), e);
    }
   ...
}
```

发现在`newProxyInstance`方法中调用了`getProxyClass0(loader, intfs)`方法，我们跟进去这个方法看一下：

```java
/**
 * Generate a proxy class.  Must call the checkProxyAccess method
 * to perform permission checks before calling this.
 */
private static Class<?> getProxyClass0(ClassLoader loader,
                                       Class<?>... interfaces) {
    if (interfaces.length > 65535) {
        throw new IllegalArgumentException("interface limit exceeded");
    }

    // If the proxy class defined by the given loader implementing
    // the given interfaces exists, this will simply return the cached copy;
    // otherwise, it will create the proxy class via the ProxyClassFactory
    return proxyClassCache.get(loader, interfaces);
}
```

代码逻辑很简单，做了两个事情：

1. 检查类的接口数量是否超过`65535`,接口个数用 2 个 byte 存储，最大支持 65535 个。
2. 从 `proxyClassCache` 缓存中去取，从注释中可知，如果缓存没有就会调用`ProxyClassFactory`去创建。

我们现在就来简单分析一下`proxyClassCache.get(loader, interfaces)`里面的逻辑：

```java
public V get(K key, P parameter) {
    Objects.requireNonNull(parameter);

    expungeStaleEntries();

    Object cacheKey = CacheKey.valueOf(key, refQueue);

    // lazily install the 2nd level valuesMap for the particular cacheKey
    ConcurrentMap<Object, Supplier<V>> valuesMap = map.get(cacheKey);
    if (valuesMap == null) {
        ConcurrentMap<Object, Supplier<V>> oldValuesMap
            = map.putIfAbsent(cacheKey,
                              valuesMap = new ConcurrentHashMap<>());
        if (oldValuesMap != null) {
            valuesMap = oldValuesMap;
        }
    }

    // create subKey and retrieve the possible Supplier<V> stored by that
    // subKey from valuesMap
    Object subKey = Objects.requireNonNull(subKeyFactory.apply(key, parameter));
    Supplier<V> supplier = valuesMap.get(subKey);
    Factory factory = null;
    // 这里是一个 while（true）
    while (true) {
        // 如果创建 factory（这里指ProxyClassFactory） 成功，就调用 factory.get()方法
        if (supplier != null) {
            // supplier might be a Factory or a CacheValue<V> instance
            //
            V value = supplier.get();
            if (value != null) {
                return value;
            }
        }
        // else no supplier in cache
        // or a supplier that returned null (could be a cleared CacheValue
        // or a Factory that wasn't successful in installing the CacheValue)

        // lazily construct a Factory
        if (factory == null) {
            factory = new Factory(key, parameter, subKey, valuesMap);
        }

        if (supplier == null) {
            supplier = valuesMap.putIfAbsent(subKey, factory);
            if (supplier == null) {
                // successfully installed Factory
                supplier = factory;
            }
            // else retry with winning supplier
        } else {
            if (valuesMap.replace(subKey, supplier, factory)) {
                // successfully replaced
                // cleared CacheEntry / unsuccessful Factory
                // with our Factory
                supplier = factory;
            } else {
                // retry with current supplier
                supplier = valuesMap.get(subKey);
            }
        }
    }
}
```

代码可能有点长，其实逻辑就是为了调用`ProxyClassFactory.apply()`去生成代理类。我们从`while(true)`处将代码分割成两个部分来看：

1. 前半部分，是从缓存中去取`ProxyClassFactory`，如果创建成功了，则可以取到（缓存中的 key 这里不分析了）
2. 然后看 `while(true)` 代码块中的逻辑，`if (supplier != null)`这个判断，如果缓存中创建了`ProxyClassFactory`就会执行`supplier.get()`并且终止循环；如果没有，则会执行`new Factory(key, parameter, subKey, valuesMap);`去创建`factory`，然后将其放入缓存`supplier`中，然后继续循环，这个时候就会执行`if (supplier != null)`代码块中的逻辑，我们再来分析一下这个代码块里面的代码：

```java
if (supplier != null) {
    // supplier might be a Factory or a CacheValue<V> instance
    V value = supplier.get();
    if (value != null) {
        return value;
    }
}
```

跟进 `supplier.get()`方法去看一下，我们从上面的分析可以知道这里的`supplier`其实就是一个`Factory`，所以我们看`Factory`的实现，重点看`get()`方法：

```java
private final class Factory implements Supplier<V> {
       ...
        @Override
        public synchronized V get() { // serialize access
            ...
            // create new value
            V value = null;
            try {
                value = Objects.requireNonNull(valueFactory.apply(key, parameter));
            } finally {
                if (value == null) { // remove us on failure
                    valuesMap.remove(subKey, this);
                }
            }
            // the only path to reach here is with non-null value
            assert value != null;

            // wrap value with CacheValue (WeakReference)
            CacheValue<V> cacheValue = new CacheValue<>(value);

            // put into reverseMap
            reverseMap.put(cacheValue, Boolean.TRUE);

            // try replacing us with CacheValue (this should always succeed)
            if (!valuesMap.replace(subKey, this, cacheValue)) {
                throw new AssertionError("Should not reach here");
            }

            // successfully replaced us with new CacheValue -> return the value
            // wrapped by it
            return value;
        }
    }
```

我们注意到，代码中的重点是在`Objects.requireNonNull(valueFactory.apply(key, parameter));`，那这个代码中的`valueFactory`是什么呢？我们在`Proxy`中，来看一下`proxyClassCache`的定义

```java
 private static final WeakCache<ClassLoader, Class<?>[], Class<?>>
        proxyClassCache = new WeakCache<>(new KeyFactory(), new ProxyClassFactory());
```

`WeakCache`中第二个参数是`new ProxyClassFactory()` ，再来看一下对应的构造器：

```java
public WeakCache(BiFunction<K, P, ?> subKeyFactory,
                 BiFunction<K, P, V> valueFactory) {
    this.subKeyFactory = Objects.requireNonNull(subKeyFactory);
    this.valueFactory = Objects.requireNonNull(valueFactory);
}
```

这时候明白了吗？其实 `valueFactory`就是`ProxyClassFactory()`

明白了这一点，就来分析一下`valueFactory.apply(key, parameter)`到底执行了什么？我们直接看`ProxyClassFactory`的代码

```java
private static final class ProxyClassFactory
    implements BiFunction<ClassLoader, Class<?>[], Class<?>>
{
    // prefix for all proxy class names
    private static final String proxyClassNamePrefix = "$Proxy";

    // next number to use for generation of unique proxy class names
    private static final AtomicLong nextUniqueNumber = new AtomicLong();

    @Override
    public Class<?> apply(ClassLoader loader, Class<?>[] interfaces) {

        ...

        /*
         * Generate the specified proxy class.
         */
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
            proxyName, interfaces, accessFlags);
        try {
            return defineClass0(loader, proxyName,
                                proxyClassFile, 0, proxyClassFile.length);
        } catch (ClassFormatError e) {
            throw new IllegalArgumentException(e.toString());
        }
    }
}
```

纵观全览，不难分析，代码中其实就是在创建`$Proxy`这个中间代理类，其中`byte[] proxyClassFile`是代码块中组装完成之后的类的字节码文件数据，通过`ProxyGenerator.generateProxyClass()`生成；然后通过`classloader`动态加载字节码，并生成动态代理类的`Class`实例，并返回。

我们再跟进`ProxyGenerator.generateProxyClass()`方法，来看看在生成代理类过程中的处理逻辑，看重点代码：。

```java
 public static byte[] generateProxyClass(final String var0, Class<?>[] var1, int var2) {
    ProxyGenerator var3 = new ProxyGenerator(var0, var1, var2);
    final byte[] var4 = var3.generateClassFile();
    ...

    return var4;
}
```

可以发现其代码调用了`var3.generateClassFile()`去生成`Class`文件，所以我们跟进`generateClassFile()`方法，看重点内容：

```java
private byte[] generateClassFile() {
    this.addProxyMethod(hashCodeMethod, Object.class);
    this.addProxyMethod(equalsMethod, Object.class);
    this.addProxyMethod(toStringMethod, Object.class);
    Class[] var1 = this.interfaces;
    int var2 = var1.length;

    int var3;
    Class var4;
    for(var3 = 0; var3 < var2; ++var3) {
        var4 = var1[var3];
        Method[] var5 = var4.getMethods();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Method var8 = var5[var7];
            this.addProxyMethod(var8, var4);
        }
    }
    ...
}
```

代码有点长，这里就不全部展开了，有兴趣的朋友可以跟进去详细看一下。从代码中我们大致可以看出来，在生成代理类的过程中，还添加了`hashCode、equals、toString`这三个方法，然后后面的逻辑就是将代理对象中的所有接口进行迭代，将其所有的方法都重新生成代理方法；然后生成字节码。

最后再将代理类加载到`JVM`中。

### 看一下`JDK Proxy`生成的代理类`$Proxy`

我们通过下面这段代码，将`$Proxy`文件输出到文件：

```java
@Test
public void test1(){
    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    RealSubject realSubject = new RealSubject();

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
```

通过`IDEA`工具查看`$Proxy0`，印证一下我们之前的分析:

```java
public final class $Proxy0 extends Proxy implements Subject {
    private static Method m1;
    private static Method m2;
    private static Method m3;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) throws  {
        super(var1);
    }

    public final boolean equals(Object var1) throws  {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final String toString() throws  {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void request() throws  {
        try {
            super.h.invoke(this, m3, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws  {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m3 = Class.forName("com.eamon.javadesignpatterns.proxy.dynamic.jdk.Subject").getMethod("request");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
```

### 总结

总结一下`JDK Proxy`的实现步骤：

1. 拿到被代理对象的引用，并获取它的所有接口（通过反射）
2. `JDK Proxy` 类重新生成一个新的类，同时新的类要实现被代理类的所有实现的接口，还有`hashCode、equals、toString`这三个方法
3. 动态生成`Java`代码，把新加的业务逻辑方法由一定的逻辑代码去调用（在代码中体现）
4. 编译新生成的`Java`代码的 `.class`文件
5. 重新加载到`JVM`中运行

## 仿真手写 JDK Proxy

在明白了上面的原理之后，其实我们就可以尝试手动来实现一个`JDK Proxy`：

我们参照`JDK Proxy`实现原理分析一下需要动手编写哪些内容：

- 首先我们需要有一个代理类`MimeProxy`
- 然后从代理类出发，需要有`newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this)`这一个方法，方法参数为：`(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)`，所以我们需要创建一个`ClassLoader`、`InvocationHandler`;

下面来一步一步创建：

1. 先创建`MimeClassLoader`类，并实现`findClass()`方法：

```java
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
```

2. 创建 `MimeInvocationHandler` 类：

```java
/**
 * @author eamon.zhang
 * @date 2019-10-10 下午2:46
 */
public interface MimeInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
```

3. 创建`MimeProxy`类：

```java
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

    /**
     * 生成 代理类
     *
     * @param interfaces
     * @return
     */
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

    /**
     * 获取返回值类型
     *
     * @param returnClass
     * @return
     */
    private static String getReturnEmptyCode(Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "return 0;";
        } else if (returnClass == void.class) {
            return "";
        } else {
            return "return null;";
        }
    }

    /**
     * 拼接 invocationHandler 执行代码
     *
     * @param code
     * @param returnClass
     * @return
     */
    private static String getCaseCode(String code, Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "((" + mappings.get(returnClass).getName() + ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    /**
     * 判断是否有返回值
     *
     * @param clazz
     * @return
     */
    private static boolean hasReturnValue(Class<?> clazz) {
        return clazz != void.class;
    }

    /**
     * 首字母转换为小写
     *
     * @param src
     * @return
     */
    private static String toLowerFirstCase(String src) {
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
```

这样子就编写了一个属于自己的动态代理，当然，代理方法还不完善，只是针对本示例进行了编写，有兴趣的朋友可以试试将其改为更通用的代码。

# CGlib 动态代理

下面来看一下 CGlib 的动态代理的使用

## 使用

先创建`RealSubject`类，注意，这个类不用实现任何接口：

```java
/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:22
 */
public class RealSubject {
    public void request(){
        System.out.println("真实处理逻辑！");
    }
}
```

然后创建`RealSubjectCglibDynamicProxy` 代理类，它必须实现`MethodInterceptor`接口：

```java
/**
 * @author eamon.zhang
 * @date 2019-10-09 下午4:23
 */
public class RealSubjectCglibDynamicProxy implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) {
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 要把哪个设置为即将生成的新类父类
        enhancer.setSuperclass(clazz);
        // 设置回调对象
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object invokeSuper = proxy.invokeSuper(obj, args);
        after();
        return invokeSuper;
    }

    private void before() {
        System.out.println("前置增强！");
    }

    private void after() {
        System.out.println("后置增强！");
    }
}
```

这样，一个简单的`CGlib`动态代理实现就完成了，我们现在来创建测试代码：

```java
@Test
public void test(){
    RealSubjectCglibDynamicProxy proxy = new RealSubjectCglibDynamicProxy();
    RealSubject instance = (RealSubject) proxy.getInstance(RealSubject.class);
    instance.request();
}
```

测试结果：

```java
前置增强！
真实处理逻辑！
后置增强！
```

## 原理分析

不管是`JDK Proxy`还是`CGlib`，他们的核心内容都是去创建代理类，所以我们只要去了解其创建代理类的过程就 OK 了。

从上面简单的使用示例可以知道，要使用 CGlib 动态代理，代理类必须要实现`MethodInterceptor`（方法拦截器），`MethodInterceptor`接口源码如下：

```java
/**
 * General-purpose {@link Enhancer} callback which provides for "around advice".
 * @author Juozas Baliuka <a href="mailto:baliuka@mwm.lt">baliuka@mwm.lt</a>
 * @version $Id: MethodInterceptor.java,v 1.8 2004/06/24 21:15:20 herbyderby Exp $
 */
public interface MethodInterceptor
extends Callback
{
    /**
     * All generated proxied methods call this method instead of the original method.
     * The original method may either be invoked by normal reflection using the Method object,
     * or by using the MethodProxy (faster).
     * @param obj "this", the enhanced object
     * @param method intercepted Method
     * @param args argument array; primitive types are wrapped
     * @param proxy used to invoke super (non-intercepted method); may be called
     * as many times as needed
     * @throws Throwable any exception may be thrown; if so, super method will not be invoked
     * @return any value compatible with the signature of the proxied method. Method returning void will ignore this value.
     * @see MethodProxy
     */
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                               MethodProxy proxy) throws Throwable;

}
```

接口中只有一个`intercept`方法，其中传入的参数：

1. `obj` 表示增强的对象，即实现这个接口类的一个对象；
2. `method` 表示要被拦截的方法；
3. `args` 表示方法参数；
4. `proxy` 表示要触发父类的方法对象；

在创建代理对象的逻辑`getInstance(Class<?> clazz)`中，调用了`enhancer.create()`方法，我们跟进源码看一下：

```java
/**
 * Generate a new class if necessary and uses the specified
 * callbacks (if any) to create a new object instance.
 * Uses the no-arg constructor of the superclass.
 * @return a new instance
 */
public Object create() {
    classOnly = false;
    argumentTypes = null;
    return createHelper();
}
```

源码注释内容翻译：如有必要，生成一个新类，并使用指定的回调（如果有）来创建一个新的对象实例。 使用的父类的参数的构造方法来实例化父类。

它的核心内容是在`createHelper();`方法中：

```java
private Object createHelper() {
    preValidate();
    Object key = KEY_FACTORY.newInstance((superclass != null) ? superclass.getName() : null,
            ReflectUtils.getNames(interfaces),
            filter == ALL_ZERO ? null : new WeakCacheKey<CallbackFilter>(filter),
            callbackTypes,
            useFactory,
            interceptDuringConstruction,
            serialVersionUID);
    this.currentKey = key;
    Object result = super.create(key);
    return result;
}
```

`preValidate()`方法的作用是，前置校验，校验`callbackTypes、filter`是否为空，以及为空时的处理。

然后通过`KEY_FACTORY.newInstance()`方法创建`EnhancerKey`对象，并将其作为`super.create(key)`方法的参数传入，我们来看一下这个`create()`方法，发现它是`Enhancer`类的父类`AbstractClassGenerator`中的一个方法：

```java
protected Object create(Object key) {
    try {
        ClassLoader loader = getClassLoader();
        Map<ClassLoader, ClassLoaderData> cache = CACHE;
        ClassLoaderData data = cache.get(loader);
        if (data == null) {
            synchronized (AbstractClassGenerator.class) {
                cache = CACHE;
                data = cache.get(loader);
                if (data == null) {
                    Map<ClassLoader, ClassLoaderData> newCache = new WeakHashMap<ClassLoader, ClassLoaderData>(cache);
                    data = new ClassLoaderData(loader);
                    newCache.put(loader, data);
                    CACHE = newCache;
                }
            }
        }
        this.key = key;
        Object obj = data.get(this, getUseCache());
        if (obj instanceof Class) {
            return firstInstance((Class) obj);
        }
        return nextInstance(obj);
    } catch (RuntimeException e) {
        throw e;
    } catch (Error e) {
        throw e;
    } catch (Exception e) {
        throw new CodeGenerationException(e);
    }
}
```

这个方法在最后调用了 `nextInstance(obj)` 方法，它对应的实现，是在`Enhancer`类中：

```java
protected Object nextInstance(Object instance) {
    EnhancerFactoryData data = (EnhancerFactoryData) instance;

    if (classOnly) {
        return data.generatedClass;
    }

    Class[] argumentTypes = this.argumentTypes;
    Object[] arguments = this.arguments;
    if (argumentTypes == null) {
        argumentTypes = Constants.EMPTY_CLASS_ARRAY;
        arguments = null;
    }
    return data.newInstance(argumentTypes, arguments, callbacks);
}
```

这里又调用了`data.newInstance(argumentTypes, arguments, callbacks)`方法，第一个参数为代理对象的构造器类型，第二个为代理对象构造方法参数，第三个为对应回调对象。源码如下：

```java
public Object newInstance(Class[] argumentTypes, Object[] arguments, Callback[] callbacks) {
    setThreadCallbacks(callbacks);
    try {
        // Explicit reference equality is added here just in case Arrays.equals does not have one
        if (primaryConstructorArgTypes == argumentTypes ||
                Arrays.equals(primaryConstructorArgTypes, argumentTypes)) {
            // If we have relevant Constructor instance at hand, just call it
            // This skips "get constructors" machinery
            return ReflectUtils.newInstance(primaryConstructor, arguments);
        }
        // Take a slow path if observing unexpected argument types
        return ReflectUtils.newInstance(generatedClass, argumentTypes, arguments);
    } finally {
        // clear thread callbacks to allow them to be gc'd
        setThreadCallbacks(null);
    }

}
```

我们发现这里面的逻辑的意思就是，根据传进来的参数，通过反射来生成对象，我们可以利用`cglib`的代理类可以将内存中的 `class` 文件写入本地磁盘：

```java
@Test
public void test1(){
    //利用 cglib 的代理类可以将内存中的 class 文件写入本地磁盘
    System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/eamon.zhang/Documents/cglib");
    RealSubjectCglibDynamicProxy proxy = new RealSubjectCglibDynamicProxy();
    RealSubject instance = (RealSubject) proxy.getInstance(RealSubject.class);
    instance.request();
}
```

执行之后，在对应的目录中可以看到生成了下图中这三个`.class`文件：

![](https://user-gold-cdn.xitu.io/2019/10/14/16dc93b3c83d843b?w=898&h=168&f=png&s=157985)

通过调试跟踪，我们发现 `RealSubject$$EnhancerByCGLIB$$5389cdca` 就是 `CGLib`生成的代理类，继承了 `RealSubject` 类。通过`IDEA`查看该源码：

```java
public class RealSubject$$EnhancerByCGLIB$$5389cdca extends RealSubject implements Factory {
    ...
    static void CGLIB$STATICHOOK1() {
        CGLIB$THREAD_CALLBACKS = new ThreadLocal();
        CGLIB$emptyArgs = new Object[0];
        Class var0 = Class.forName("com.eamon.javadesignpatterns.proxy.dynamic.cglib.RealSubject$$EnhancerByCGLIB$$5389cdca");
        Class var1;
        CGLIB$request$0$Method = ReflectUtils.findMethods(new String[]{"request", "()V"}, (var1 = Class.forName("com.eamon.javadesignpatterns.proxy.dynamic.cglib.RealSubject")).getDeclaredMethods())[0];
        CGLIB$request$0$Proxy = MethodProxy.create(var1, var0, "()V", "request", "CGLIB$request$0");
        Method[] var10000 = ReflectUtils.findMethods(new String[]{"equals", "(Ljava/lang/Object;)Z", "toString", "()Ljava/lang/String;", "hashCode", "()I", "clone", "()Ljava/lang/Object;"}, (var1 = Class.forName("java.lang.Object")).getDeclaredMethods());
        CGLIB$equals$1$Method = var10000[0];
        CGLIB$equals$1$Proxy = MethodProxy.create(var1, var0, "(Ljava/lang/Object;)Z", "equals", "CGLIB$equals$1");
        CGLIB$toString$2$Method = var10000[1];
        CGLIB$toString$2$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/String;", "toString", "CGLIB$toString$2");
        CGLIB$hashCode$3$Method = var10000[2];
        CGLIB$hashCode$3$Proxy = MethodProxy.create(var1, var0, "()I", "hashCode", "CGLIB$hashCode$3");
        CGLIB$clone$4$Method = var10000[3];
        CGLIB$clone$4$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/Object;", "clone", "CGLIB$clone$4");
    }

    final void CGLIB$request$0() {
        super.request();
    }

    public final void request() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$request$0$Method, CGLIB$emptyArgs, CGLIB$request$0$Proxy);
        } else {
            super.request();
        }
    }
    ...
}
```

我们通过代理类的源码可以看到，代理类会获得所有在父类继承来的方法，并且会有 `MethodProxy` 与之对应，比如 `Method CGLIB$request$0$Method`、`MethodProxy CGLIB$request$0$Proxy`这些方法在代理类的 `reuqest()`中都有调用。

**调用过程:** 代理对象调用 `this.request()`方法 -> 调用拦截器 -> `methodProxy.invokeSuper` -> `CGLIB$request$0()` -> 被代理对象 `request()`方法。 此时，我们发现拦截器 `MethodInterceptor` 中就是由 `MethodProxy` 的 `invokeSuper` 方法调用代理方法的。

MethodProxy 非常关键，我们分析一下它具体做了什么：

```java
public class MethodProxy {
    private Signature sig1;
    private Signature sig2;
    private CreateInfo createInfo;

    private final Object initLock = new Object();
    private volatile FastClassInfo fastClassInfo;

    /**
     * For internal use by {@link Enhancer} only; see the {@link net.sf.cglib.reflect.FastMethod} class
     * for similar functionality.
     */
    public static MethodProxy create(Class c1, Class c2, String desc, String name1, String name2) {
        MethodProxy proxy = new MethodProxy();
        proxy.sig1 = new Signature(name1, desc);
        proxy.sig2 = new Signature(name2, desc);
        proxy.createInfo = new CreateInfo(c1, c2);
        return proxy;
    }
    ...

    private static class CreateInfo
    {
        Class c1;
        Class c2;
        NamingPolicy namingPolicy;
        GeneratorStrategy strategy;
        boolean attemptLoad;

        public CreateInfo(Class c1, Class c2)
        {
            this.c1 = c1;
            this.c2 = c2;
            AbstractClassGenerator fromEnhancer = AbstractClassGenerator.getCurrent();
            if (fromEnhancer != null) {
                namingPolicy = fromEnhancer.getNamingPolicy();
                strategy = fromEnhancer.getStrategy();
                attemptLoad = fromEnhancer.getAttemptLoad();
            }
        }
    }
    ...
```

继续看`invokeSuper()`方法：

```java
public Object invokeSuper(Object obj, Object[] args) throws Throwable {
    try {
        init();
        FastClassInfo fci = fastClassInfo;
        return fci.f2.invoke(fci.i2, obj, args);
    } catch (InvocationTargetException e) {
        throw e.getTargetException();
    }
}

private static class FastClassInfo
{
    FastClass f1;
    FastClass f2;
    int i1;
    int i2;
}
```

上面代码调用过程就是获取到代理类对应的 `FastClass`，并执行了代理方法。还记得之前生成三个 `class` 文件吗？`RealSubject$$EnhancerByCGLIB$$5389cdca$$FastClassByCGLIB$$57b94d72.class`就是代理类的 `FastClass` ，`RealSubject$$FastClassByCGLIB$$ed23432.class`就是被代理类的`FastClass`。

`CGLib` 动态代理执行代理方法效率之所以比 `JDK` 的高是因为 `Cglib` 采用了 `FastClass` 机 制，它的原理简单来说就是:

- 为代理类和被代理类各生成一个 Class，这个 Class 会为代理类或被代理类的方法分配一个 `index(int 类型)`。这个 `index` 当做一个入参，`FastClass`就可以直接定位要调用的方法直接进行调用，这样省去了反射调用，所以调用效率比 `JDK`动态代理通过反射调用高。

至此，Cglib 动态代理的原理我们就基本搞清楚了，如果对代码细节有兴趣的小伙伴可以再自行深入研究。

# JDK Proxy 与 CGlib 比较

1. `JDK` 动态代理是**实现**了被代理对象的接口，`CGLib` 是**继承**了被代理对象。
2. `JDK` 和 `CGLib` 都是在运行期生成字节码，`JDK` 是直接写 `Class` 字节码，`CGLib` 使用 `ASM` 框架写 `Class` 字节码，`Cglib` 代理实现更复杂，**生成代理类** 比 `JDK` 效率低。
3. `JDK` 调用代理方法，是通过反射机制调用，`CGLib` 是通过 `FastClass` 机制直接调用方法， `CGLib` **执行效率** 更高

# 代理模式与 Spring

## Spring 中的代理选择原则

1. 当 `Bean` 有实现接口时，`Spring` 就会用 `JDK` 的动态代理
2. 当 `Bean` 没有实现接口时，`Spring` 选择 `CGLib`。
3. `Spring` 可以通过配置强制使用 `CGLib`，只需在 `Spring` 的配置文件中加入如下代码:

```xml
<aop:aspectj-autoproxy proxy-target-class="true"/>
```

参考资料：https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html

# 总结

## 静态代理和动态的本质区别

1. 静态代理只能通过**手动**完成代理操作，如果被代理类**增加新的方法**，代理类需要**同步新增**，**违背开闭原则**。
2. 动态代理采用在**运行时动态生成**代码的方式，取消了对被代理类的扩展限制，**遵循开闭原则**。
3. 若动态代理要对目标类的增强逻辑扩展，结合策略模式，只需要新增策略类便可完成，无需修改代理类的代码。

## 代理模式的优缺点

### 优点

1. 代理模式能将代理对象与真实被调用的目标对象分离。
2. 一定程度上降低了系统的耦合度，扩展性好。
3. 可以起到保护目标对象的作用。
4. 可以对目标对象的功能增强

### 缺点

1. 代理模式会造成系统设计中类的数量增加。
2. 在客户端和目标对象增加一个代理对象，会造成请求处理速度变慢。
3. 增加了系统的复杂度。

---

本篇文章的源码目录：https://github.com/eamonzzz/java-advanced/tree/master/01.DesignPatterns/design-patterns/src/main/java/com/eamon/javadesignpatterns/proxy

测试类源码目录：https://github.com/eamonzzz/java-advanced/tree/master/01.DesignPatterns/design-patterns/src/test/java/com/eamon/javadesignpatterns/proxy

---

> 欢迎大家 star 源码，共同进步，我会按照 git 上的大纲在学习的同时，记录文章与源码~

> 博主刚开始写博客不久，文中若有错误或者有任何的建议，请在留言中指出，向大家学习~
