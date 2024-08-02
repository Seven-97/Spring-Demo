package com.seven.springmybatis.controller;

import com.seven.springmybatis.entity.User;
import com.seven.springmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Seven
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/lsit", method = RequestMethod.GET)
    public String getUserList() {
        for (User user : userService.queryAll(0, 10)) {
            System.out.println(user);
        }

        return "ok";
    }

}
