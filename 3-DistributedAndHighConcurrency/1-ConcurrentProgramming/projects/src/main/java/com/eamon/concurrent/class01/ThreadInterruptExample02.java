package com.eamon.concurrent.class01;

import java.util.concurrent.TimeUnit;

/**
 * 线程 中断 示例
 *
 * @author eamonzzz
 * @date 2021-07-21 23:08
 */
public class ThreadInterruptExample02 {
    private static int x;
    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadInterrupt(), "interruptDemo-1");
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadInterrupt extends Thread {
        @Override
        public void run() {
            // 默认情况下，isInterrupted 方法返回 false，通过调用主线程的 thread.interrupt(); 方法变成了 true
            while (!Thread.currentThread().isInterrupted()) {
                x++;
            }
            System.out.println("NUM: " + x);
        }
    }
}
