package com.seven.springframeworkaopannojdk.app;

import com.seven.springframeworkaopannojdk.service.IJdkProxyService;
import com.seven.springframeworkaopannojdk.service.impl.JdkProxyDemoServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // create and configure beans
        ApplicationContext context = new AnnotationConfigApplicationContext("com.seven.springframeworkaopannojdk");

        // retrieve configured instance
        IJdkProxyService service = context.getBean(IJdkProxyService.class);

        // use configured instance
        service.doMethod1();
        service.doMethod2();
        try {
            service.doMethod3();
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
