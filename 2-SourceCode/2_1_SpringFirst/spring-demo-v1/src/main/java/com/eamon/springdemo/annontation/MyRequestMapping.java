package com.eamon.springdemo.annontation;

import java.lang.annotation.*;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:27
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMapping {
    String value() default "";
}
