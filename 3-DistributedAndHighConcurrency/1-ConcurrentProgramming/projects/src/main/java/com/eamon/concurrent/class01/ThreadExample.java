package com.eamon.concurrent.class01;

/**
 * @author eamonzzz
 * @date 2021-07-19 23:15
 */
public class ThreadExample extends Thread {
    int x = 0;
    @Override
    public void run() {
        int y = 0;
        y = x + 1;
    }

    public static void main(String[] args) {
        new ThreadExample().start();
    }
}
