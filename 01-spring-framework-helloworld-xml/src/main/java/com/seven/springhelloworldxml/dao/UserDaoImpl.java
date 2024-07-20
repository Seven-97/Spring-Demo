package com.seven.springhelloworldxml.dao;

import com.seven.springhelloworldxml.pojo.User;

import java.util.Collections;
import java.util.List;

/**
 * @author Seven
 */
public class UserDaoImpl{

    public List<User> findUserList() {
        return Collections.singletonList(new User("seven", 18));
    }
}
