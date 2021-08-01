package com.eamon.concurrent.class01;

/**
 * @author eamon.zhang
 * @version 1.0
 * @date 2021-08-01 16:33:06
 */
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo sd=new SynchronizedDemo();
        Thread t1=new Thread(sd);
        t1.start();
        sd.m2();
    }
    static class SynchronizedDemo implements Runnable{
        int b=0;
        @Override
        public void run() {
            try {
                m1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public synchronized void m1() throws InterruptedException {
            System.out.println("m1");
            b=100;
            Thread.sleep(3000);
            System.out.println("b="+b);
        }
        public synchronized void m2() throws InterruptedException {
            System.out.println("m2");
            Thread.sleep(1000);
            b=500;
            System.out.println("m2:b"+b);
        }
    }
}
