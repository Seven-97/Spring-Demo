package com.seven.springframeworkaopxml.app;

import com.seven.springframeworkaopxml.service.AopDemoServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("aspects.xml");

        // retrieve configured instance
        AopDemoServiceImpl service = context.getBean("demoService", AopDemoServiceImpl.class);

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
