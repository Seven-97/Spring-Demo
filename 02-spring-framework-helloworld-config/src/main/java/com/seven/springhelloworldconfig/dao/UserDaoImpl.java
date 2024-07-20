package com.seven.springhelloworldconfig.dao;


import com.seven.springhelloworldconfig.pojo.User;

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
