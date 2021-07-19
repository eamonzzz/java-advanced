package com.eamon.designprinciples.rectangle;

/**
 * @author eamon.zhang
 * @date 2019-09-26 上午10:02
 */
public class Test {
    public static void main(String[] args) {
        Square square = new Square();
        square.setLength(10);
//        resize(square);
    }

    public static void resize(Rectangle rectangle) {
        while (rectangle.getWidth() >= rectangle.getHeight()) {
            rectangle.setHeight(rectangle.getHeight() + 1);
            System.out.println("width:" + rectangle.getWidth() + ",height:" + rectangle.getHeight());
        }
        System.out.println("resize 方法结束" +
                "\nwidth:" + rectangle.getWidth() + ",height:" + rectangle.getHeight());
    }
}
