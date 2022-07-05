package com.eamon.designprinciples.book;

/**
 * @author eamon.zhang
 * @date 2019-09-25 上午10:36
 */
public class NovelDiscountBook extends NovelBook {
    public NovelDiscountBook(String name, int price, String author) {
        super(name, price, author);
    }

    public double getDiscountPrice(){
        return super.getPrice() * 0.75;
    }
}
