package com.eamon.concurrent.class02;

/**
 * @author eamon.zhang
 * @version 1.0
 * @date 2021-08-01 17:20:39
 */
public class AtomicExample {
    int i = 0;
    public void incr(){
        i++;
    }
    public static void main(String[] args) {
        Thread[] threads = new Thread[2];
        AtomicExample atomicExample = new AtomicExample();
        for (int j = 0; j < 2; j++) {
            threads[j] = new Thread(()->{
                for (int k = 0; k < 10000; k++) {
                    atomicExample.incr();
                }
            });
            threads[j].start();
        }
        try {
            threads[0].join();
            threads[1].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(atomicExample.i);
    }
}
