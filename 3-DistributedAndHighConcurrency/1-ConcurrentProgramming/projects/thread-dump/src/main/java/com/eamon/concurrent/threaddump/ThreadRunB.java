package com.eamon.concurrent.threaddump;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
class ThreadRunB extends Thread {
    @Override
    public void run() {
        System.out.println("================B===================");
        synchronized (B.B) {
            System.out.println("我要开始执行任务B。。。。" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (A.A) {
            }
            System.out.println("我在执行任务结束了B。。。。" + Thread.currentThread().getName() + ":" + B.B + ":" + A.A);
        }
    }
}
