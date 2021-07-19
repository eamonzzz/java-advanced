package com.eamon.springdemo.annontation;

import java.lang.annotation.*;

/**
 * @author eamonzzz
 * @date 2020-06-27 21:25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {
    String value() default "";
}
