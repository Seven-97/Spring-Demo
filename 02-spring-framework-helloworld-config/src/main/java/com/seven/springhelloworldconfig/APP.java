package com.seven.springhelloworldconfig;

import com.seven.springhelloworldconfig.config.BeansConfig;
import com.seven.springhelloworldconfig.pojo.User;
import com.seven.springhelloworldconfig.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * @author Seven
 */
public class APP {

    public static void main(String[] args) {
        // create and configure beans
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);

        // retrieve configured instance
        UserServiceImpl service = context.getBean("userService", UserServiceImpl.class);

        // use configured instance
        List<User> userList = service.findUserList();

        // print info from beans
        userList.forEach(a -> System.out.println(a.getName() + "," + a.getAge()));
    }

}
