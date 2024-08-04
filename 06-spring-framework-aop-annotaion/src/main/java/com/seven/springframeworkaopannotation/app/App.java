package com.seven.springframeworkaopannotation.app;


import com.seven.springframeworkaopannotation.service.IJdkProxyService;
import com.seven.springframeworkaopannotation.service.impl.CglibProxyDemoServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // create and configure beans
        ApplicationContext context = new AnnotationConfigApplicationContext("com.seven.springframeworkaopannotation");

        // cglib proxy demo
        CglibProxyDemoServiceImpl service1 = context.getBean(CglibProxyDemoServiceImpl.class);
        service1.doMethod1();
        service1.doMethod2();

        IJdkProxyService service2 = context.getBean(IJdkProxyService.class);
        service2.doMethod1();
        service2.doMethod2();
    }
}
