package com.eamon.concurrent.class01;

import java.util.concurrent.TimeUnit;

/**
 * 线程停止示例
 *
 * @author eamonzzz
 * @date 2021-07-21 22:32
 */
public class ThreadStopExample {
    /** flag 标志条件 */
    public static boolean flag = true;
    public static void main(String[] args) {
        ThreadStop threadStop = new ThreadStop();
        threadStop.start();

        try {
            // 睡眠 10 秒 之后将 flag 标志位设为 false
            TimeUnit.SECONDS.sleep(10);
            flag = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadStop extends Thread {
        @Override
        public void run() {
            while (flag) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadStop.run()");
            }
        }
    }
}
