package com.eamon.springdemo.annontation;

import java.lang.annotation.*;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
    String value() default "";
}
