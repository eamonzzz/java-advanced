package com.eamon.designprinciples.book;

/**
 * @author eamon.zhang
 * @date 2019-09-25 上午10:30
 */
public class NovelBook implements IBook {
    // 书名
    private String name;
    // 售价
    private int price;
    // 作者
    private String author;
    // 通过构造函数传递数据数据
    public NovelBook(String name, int price, String author) {
        this.name = name;
        this.price = price;
        this.author = author;
    }
    // 获取书名
    public String getName() {
        return this.name;
    }
    // 获取价格
    public double getPrice() {
        return this.price;
    }
    // 获取作者
    public String getAuthor() {
        return this.author;
    }
}
