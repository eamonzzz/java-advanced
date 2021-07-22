package com.eamon.concurrent.class01;

import java.util.concurrent.TimeUnit;

/**
 * 查看线程状态 示例
 * <p>
 * 运行程序后，通过 jps 命令，查看当前Java进程，并得到 ThreadStatusExample 进程的 PID 号
 * <p>
 * 再通过 jstack [pid] 命令，查看堆栈信息，即可查看到相应线程的状态
 *
 * @author eamonzzz
 * @date 2021-07-21 21:53
 */
public class ThreadStatusExample {

    public static void main(String[] args) {

        // TIME_WAITING
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "time_waiting").start();

        // WAITING  线程在 ThreadStatusExample 类锁上通过 wait 进行等待
        new Thread(() -> {
            while (true) {
                synchronized (ThreadStatusExample.class) {
                    try {
                        ThreadStatusExample.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "waiting").start();


        // BLOCKED  线程在 BlockDemo 加锁后，不会释放锁
        new Thread(new BlockDemo(), "BlockedDemo-01").start();

        new Thread(new BlockDemo(), "BlockedDemo-02").start();
    }

    static class BlockDemo extends Thread {
        @Override
        public void run() {
            synchronized (BlockDemo.class) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
