package com.seven.minispringsourcecod.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SevenAutowired {
    String value() default "";
}
