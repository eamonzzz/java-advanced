package com.eamon.concurrent.threaddump;

import java.util.concurrent.TimeUnit;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class ThreadStatusExample {

    public static void main(String[] args) {
        new Thread(()->{
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"TIME_WAITING").start();

        new Thread(()->{
            while(true){
                synchronized (ThreadStatusExample.class){
                    try {
                        ThreadStatusExample.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"WAITING");

        new Thread(new BlockDemo(),"BLOCKED-DEMO-01").start();
        new Thread(new BlockDemo(),"BLOCKED-DEMO-02").start();
    }

    static class BlockDemo extends Thread{
        @Override
        public void run() {
            synchronized (BlockDemo.class){
                while(true){
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
