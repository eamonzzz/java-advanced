package com.eamon.concurrent.threaddump;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class ThreadDemo implements Runnable{

    @Override
    public void run() { //回调方法
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程会被执行的代码");
    }
    public static void main(String[] args) {
        // CompletableFuture V  基于Callable/Future的优化
        new Thread(new SmsSenderTask()).start(); //不需要等待这个程序的处理结果
        System.out.println("Main方法的输出结果");
    }
}
