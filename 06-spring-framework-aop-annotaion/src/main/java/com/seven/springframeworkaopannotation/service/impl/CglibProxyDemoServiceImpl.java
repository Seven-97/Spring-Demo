package com.seven.springframeworkaopannotation.service.impl;

import com.seven.springframeworkaopannotation.anno.LogAspectAnno;
import org.springframework.stereotype.Service;

/**
 * @author Seven
 */
@Service
public class CglibProxyDemoServiceImpl {

    @LogAspectAnno()
    public void doMethod1() {
        System.out.println("CglibProxyDemoServiceImpl.doMethod1()");
    }

    public String doMethod2() {
        System.out.println("CglibProxyDemoServiceImpl.doMethod2()");
        return "hello world";
    }

}
