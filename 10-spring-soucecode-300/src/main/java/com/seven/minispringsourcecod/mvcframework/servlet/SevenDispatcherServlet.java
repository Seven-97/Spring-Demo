package com.seven.minispringsourcecod.mvcframework.servlet;


import com.seven.minispringsourcecod.mvcframework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by Tom.
 */
public class SevenDispatcherServlet extends HttpServlet {

    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();

    //保存扫描的所有的类名
    private List<String> classNames = new ArrayList<String>();

    //传说中的IOC容器，我们来揭开它的神秘面纱
    //为了简化程序，暂时不考虑ConcurrentHashMap
    // 主要还是关注设计思想和原理
    private Map<String,Object> ioc = new HashMap<String,Object>();

    //保存url和Method的对应关系
    private Map<String,Method> handlerMapping = new HashMap<String,Method>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //6、根据url调用method
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detail: " + Arrays.toString(e.getStackTrace()));
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");

        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 Not Found!!");
            return;
        }

        Method method = this.handlerMapping.get(url);

        Map<String,String[]> paramsMap = req.getParameterMap();

        //实参列表
        //实参列表要根据形参列表才能决定，首先得拿到形参列表
        Class<?> [] paramterTypes = method.getParameterTypes();

        Object [] parameValues = new Object[paramterTypes.length];
        for (int i = 0; i <paramterTypes.length; i ++){
            Class paramterType = paramterTypes[i];
            if(paramterType == HttpServletRequest.class){
                parameValues[i] = req;
                continue;
            }else if(paramterType == HttpServletResponse.class){
                parameValues[i] = resp;
                continue;
            }else if(paramterType == String.class){
                Annotation[][] pa = method.getParameterAnnotations();
                for (int j = 0; j < pa.length; j ++){
                    for (Annotation a : pa[i]) {
                        if(a instanceof SevenRequestParam){
                            String paramName = ((SevenRequestParam) a).value();
                            if(!"".equals(paramName.trim())){
                                String value = Arrays.toString(paramsMap.get(paramName))
                                        .replaceAll("\\[|\\]","")
                                        .replaceAll("\\s",",");
                                parameValues[i] = value;
                            }
                        }
                    }
                }
            }
        }

        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName),parameValues);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        //工厂类  GPApplicationContext  IOC、DI

        //=========== IOC ===========

        //1、加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        
        //2、扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        
        //3、初始化扫描到的类，并且放入到IOC容器之中
        doInstance();

        //=========  DI =========

        //4、完成自动化的依赖注入
        doAutowired();

        //======= MVC =============
        
        //5、初始化HandlerMapping
        doInitHandlerMapping();

        System.out.println("GP Spring framework is init.");

    }

    //初始化url和Method的一对一对应关系
    private void doInitHandlerMapping() {
        if(ioc.isEmpty()){return;}

        for (Map.Entry<String,Object> entry : ioc.entrySet()) {
           Class<?> clazz = entry.getValue().getClass();
           if(!clazz.isAnnotationPresent(SevenController.class)){ continue; }

            //保存写在类上面的@SevenRequestMapping("/demo")
           String baseUrl = "";
           if(clazz.isAnnotationPresent(SevenRequestMapping.class)){
               SevenRequestMapping requestMapping = clazz.getAnnotation(SevenRequestMapping.class);
               baseUrl = requestMapping.value();
           }

            //默认获取所有的public方法
            for (Method method : clazz.getMethods()) {
                if(!method.isAnnotationPresent(SevenRequestMapping.class)){continue;}

                SevenRequestMapping requestMapping = method.getAnnotation(SevenRequestMapping.class);

                //  无斜杠:demoquery
                //  多个斜杠://demo//query
                String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+","/");
                handlerMapping.put(url,method);

                System.out.println("Mapped " + url + "," + method);

            }

        }
        
    }

    private void doAutowired() {
        if(ioc.isEmpty()){return;}

        for (Map.Entry<String,Object> entry : ioc.entrySet()) {
            //拿到实例的所有的字段
            //Declared 所有的，特定的 字段，包括private/protected/default
            //正常来说，普通的OOP编程只能拿到public的属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if(!field.isAnnotationPresent(SevenAutowired.class)){
                    continue;
                }
                SevenAutowired autowired = field.getAnnotation(SevenAutowired.class);
                //如果用户没有自定义beanName，默认就根据类型注入
                //这个地方省去了对类名首字母小写的情况的判断，这个作为课后作业
                //小伙伴们自己去完善
                String beanName = autowired.value().trim();
                if("".equals(beanName)){
                    //获得接口的类型，作为key待会拿这个key到ioc容器中去取值
                    beanName = field.getType().getName();
                }

                //如果是public以外的修饰符，只要加了@Autowired注解，都要强制赋值
                //反射中叫做暴力访问
                field.setAccessible(true);

                //反射调用的方式
                //给entry.getValue()这个对象的field字段，赋ioc.get(beanName)这个值
                try {
                    field.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private void doInstance() {
        if(classNames.isEmpty()){return;}

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                //什么样的类才需要初始化呢？
                //加了注解的类，才初始化，怎么判断？
                //为了简化代码逻辑，主要体会设计思想，只举例 @Controller和@Service,
                // @Componment...就不一一举例了
                if(clazz.isAnnotationPresent(SevenController.class)){
                    Object instance = clazz.newInstance();
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    //key-value
                    //class类名的首字母小写
                    ioc.put(beanName,instance);
                }else if(clazz.isAnnotationPresent(SevenService.class)) {
                    //1、默认就根据beanName类名首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());

                    //2、使用自定义的beanName
                    SevenService service = clazz.getAnnotation(SevenService.class);
                    if(!"".equals(service.value())){
                        beanName = service.value();
                    }

                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);

                    //3、根据包名.类名作为beanName
                    for (Class<?> i : clazz.getInterfaces()) {
                        if(ioc.containsKey(i.getName())){
                            throw new Exception("The beanName is exists!!");
                        }
                        //把接口的类型直接当成key了
                        ioc.put(i.getName(),instance);
                    }

                }else {
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //如果类名本身是小写字母，确实会出问题
    //但是我要说明的是：这个方法是我自己用，private的
    //传值也是自己传，类也都遵循了驼峰命名法
    //默认传入的值，存在首字母小写的情况，也不可能出现非字母的情况

    //为了简化程序逻辑，就不做其他判断了，大家了解就OK
    //其实用写注释的时间都能够把逻辑写完了
    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }

    //扫描出相关的类
    private void doScanner(String scanPackage) {
        //scanPackage = com.seven.minispringsourcecod.demo ，存储的是包路径
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));

        //转换为文件路径，实际上就是把.替换为/就OK了
        //classpath下不仅有.class文件， .xml文件  .properties文件
        File classPath = new File(url.getFile());

        for (File file : classPath.listFiles()) {

            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else {
                //变成包名.类名
                //Class.forname()
                if (!file.getName().endsWith(".class")) {  continue; }
                classNames.add(scanPackage + "." + file.getName().replace(".class", ""));
            }
        }
    }

    //加载配置文件
    private void doLoadConfig(String contextConfigLocation) {
        //直接从类路径下找到Spring主配置文件所在的路径
        //并且将其读取出来放到Properties对象中
        //相对于scanPackage=com.gupaoedu.demo 从文件中保存到了内存中
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
