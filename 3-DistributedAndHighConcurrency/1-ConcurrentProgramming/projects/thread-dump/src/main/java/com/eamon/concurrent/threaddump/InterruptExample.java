package com.eamon.concurrent.threaddump;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class InterruptExample implements Runnable{

//    volatile jint _interrupted;     // Thread.isInterrupted state
    volatile boolean flag=false;

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new InterruptExample());
        t1.start();
        Thread.sleep(2000);
        //Main线程来决定t1线程的生死
        t1.interrupt();//发送一个中断信号, 中断标记变为true
        // 让当前线程中断状态的复位。
//        Thread.interrupted();
    }

    @Override
    public void run() {
        //如果让线程友好结束，只有当前run方法中的程序知道.
        //Thread.currentThread().isInterrupted()获取中断状态
       try {
           Thread.sleep(1000);
       } catch (InterruptedException e) { //中断标记变为false
           e.printStackTrace();
           //TODO 临时保存一些数据.
           //把中断标记修改为true
//             Thread.currentThread().interrupt();
       }
       System.out.println(Thread.currentThread().getName()+"--");
    }
}
