package com.eamon.javadesignpatterns.proxy.staticproxy;

/**
 * @author eamon.zhang
 * @date 2019-10-09 下午3:57
 */
public class RealSubjectProxy implements Subject {

    private Subject target;

    public RealSubjectProxy(Subject target) {
        this.target = target;
    }

    @Override
    public void request() {
        // 代理目标对象时，可以在具体执行方法之前和之后添加相应的功能，也叫增强
        before();
        this.target.request();
        after();
    }

    private void after() {
        System.out.println("后置增强！");
    }

    private void before() {
        System.out.println("前置增强！");
    }
}
