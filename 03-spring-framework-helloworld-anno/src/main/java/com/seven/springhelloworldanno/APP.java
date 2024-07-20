package com.seven.springhelloworldanno;

import com.seven.springhelloworldanno.service.UserServiceImpl;
import com.seven.springhelloworldanno.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * @author Seven
 */
public class APP {

    public static void main(String[] args) {
        // create and configure beans
        ApplicationContext context = new AnnotationConfigApplicationContext("com.seven.springhelloworldanno");

        // retrieve configured instance
        UserServiceImpl service = context.getBean(UserServiceImpl.class);

        // use configured instance
        List<User> userList = service.findUserList();

        // print info from beans
        userList.forEach(a -> System.out.println(a.getName() + "," + a.getAge()));
    }

}
