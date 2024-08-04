package com.seven.springframeworkaopannotation.aspect;

import com.seven.springframeworkaopannotation.anno.LogAspectAnno;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author Seven
 */
@EnableAspectJAutoProxy
@Component
@Aspect
public class LogAspect {

    /**
     * 环绕通知.
     *
     * @param pjp pjp
     * @return obj
     * @throws Throwable exception
     */
    @Around("@annotation(logaspectanno)")
    public Object doAround(ProceedingJoinPoint pjp, LogAspectAnno logaspectanno) throws Throwable {
        System.out.println("-----------------------");
        System.out.println("环绕通知: 进入方法");
        Object o = pjp.proceed();
        System.out.println("环绕通知: 退出方法");
        return o;
    }

}
