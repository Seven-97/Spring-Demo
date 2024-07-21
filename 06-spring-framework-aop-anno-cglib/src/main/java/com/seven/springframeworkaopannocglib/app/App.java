package com.seven.springframeworkaopannocglib.app;

import com.seven.springframeworkaopannocglib.service.CglibProxyDemoServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // create and configure beans
        ApplicationContext context = new AnnotationConfigApplicationContext("com.seven.springframeworkaopannocglib");

        // cglib proxy demo
        CglibProxyDemoServiceImpl service = context.getBean(CglibProxyDemoServiceImpl.class);
        service.doMethod1();
        service.doMethod2();
        try {
            service.doMethod3();
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
