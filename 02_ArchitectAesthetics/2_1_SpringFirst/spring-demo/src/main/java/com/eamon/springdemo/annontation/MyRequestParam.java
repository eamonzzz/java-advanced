package com.eamon.springdemo.annontation;

import java.lang.annotation.*;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:28
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {
    String value() default "";
}
