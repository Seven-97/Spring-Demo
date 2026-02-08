package com.seven.minispringsourcecod.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SevenRequestParam {
    String value() default "";
}
