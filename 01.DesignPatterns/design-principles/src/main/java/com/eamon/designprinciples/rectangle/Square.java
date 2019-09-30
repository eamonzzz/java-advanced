package com.eamon.designprinciples.rectangle;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午10:01
 */
public class Square implements Quadrangle {
    private long length;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public long getWidth() {
        return length;
    }

    @Override
    public long getHeight() {
        return length;
    }
}
