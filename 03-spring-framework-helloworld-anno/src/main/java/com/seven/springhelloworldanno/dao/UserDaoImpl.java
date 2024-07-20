package com.seven.springhelloworldanno.dao;


import com.seven.springhelloworldanno.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author Seven
 */
@Repository
public class UserDaoImpl{

    public List<User> findUserList() {
        return Collections.singletonList(new User("seven", 18));
    }
}
