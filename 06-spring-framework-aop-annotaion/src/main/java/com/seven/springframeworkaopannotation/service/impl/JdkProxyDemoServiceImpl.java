package com.seven.springframeworkaopannotation.service.impl;

import com.seven.springframeworkaopannotation.anno.LogAspectAnno;
import com.seven.springframeworkaopannotation.service.IJdkProxyService;
import org.springframework.stereotype.Service;

/**
 * @author Seven
 */
@Service
public class JdkProxyDemoServiceImpl implements IJdkProxyService {

    @LogAspectAnno
    @Override
    public void doMethod1() {
        System.out.println("JdkProxyServiceImpl.doMethod1()");
    }

    @Override
    public String doMethod2() {
        System.out.println("JdkProxyServiceImpl.doMethod2()");
        return "hello world";
    }
}
