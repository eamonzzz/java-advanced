package com.eamon.designprinciples.eamon;

/**
 * @author eamon.zhang
 * @date 2019-09-25 上午11:14
 */
public class EamonTest {

    public static void main(String[] args) {
        Eamon eamon = new Eamon();
        eamon.setBook(new NotreDameBook());
        eamon.read();

        eamon.setBook(new TheOldManAndTheSeaBook());
        eamon.read();
    }

}
