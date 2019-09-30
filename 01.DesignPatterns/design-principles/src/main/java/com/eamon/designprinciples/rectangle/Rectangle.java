package com.eamon.designprinciples.rectangle;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午9:59
 */
public class Rectangle implements Quadrangle {
    private long height;
    private long width;

    @Override
    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setWidth(long width) {
        this.width = width;
    }
}
