package com.seven.springmvchelloworldanno.dao;


import com.seven.springmvchelloworldanno.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author Seven
 */
@Repository
public class UserDaoImpl {

    public List<User> findUserList() {
        return Collections.singletonList(new User("seven", 18));
    }

}
