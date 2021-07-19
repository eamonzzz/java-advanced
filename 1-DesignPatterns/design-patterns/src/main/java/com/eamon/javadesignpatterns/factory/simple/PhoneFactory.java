package com.eamon.javadesignpatterns.factory.simple;

/**
 * @author eamon.zhang
 * @date 2019-09-27 上午10:54
 */
public class PhoneFactory {
//    public Phone product(String type) {
//        switch (type) {
//            case "A":
//                return new PhoneA();
//            case "B":
//                return new PhoneB();
//            default:
//                return null;
//        }
//    }

//    public Phone product(String className) {
//        try {
//            if (!(null == className || "".equals(className))) {
//                return (Phone) Class.forName(className).newInstance();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public Phone product(Class<? extends Phone> clazz) {
        try {
            if (null != clazz) {
                return clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
