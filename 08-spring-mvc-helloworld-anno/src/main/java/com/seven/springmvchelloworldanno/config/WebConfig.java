package com.seven.springmvchelloworldanno.config;

import com.seven.springmvchelloworldanno.intercepetors.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 代替SpringMVC的配置文件
 * 1. 注解扫描        2. view-controller 3. default-servlet-handler
 * 4. mvc注解驱动  5. 异常处理      6.拦截器
 */
@Configuration
@ComponentScan(basePackages = {"com.seven.springmvchelloworldanno"}) // 1. 注解扫描
@EnableWebMvc // 4. mvc注解驱动
public class WebConfig implements WebMvcConfigurer {

    //3. default-servlet-handler
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 7.拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**"); // 拦截所有路径
    }

    // 2. view-controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }


    //5. 异常处理 , 也可以另外写类加上@ControllerAdvice
    /*@Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("ArithmeticException", "error");
        exceptionResolver.setExceptionMappings(properties);
        exceptionResolver.setExceptionAttribute("exception");
        resolvers.add(exceptionResolver);
    }*/


}
