package com.eamon.designprinciples.eamon;

/**
 * @author eamon.zhang
 * @date 2019-09-25 上午11:09
 */
public class Eamon {
    private IBook iBook;
    public void setBook(IBook iBook) {
        this.iBook = iBook;
    }
    public void read(){
        iBook.read();
    }
}
