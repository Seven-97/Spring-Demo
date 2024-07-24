package com.seven.springmvchelloworldanno.controller;


import com.seven.springmvchelloworldanno.entity.User;
import com.seven.springmvchelloworldanno.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author Seven
 */
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @RequestMapping("/user")
    public String list() {
        List<User> userList = userService.findUserList();
        for (User user : userList) {
            System.out.println(user);
        }
        return "ok";
    }

}
