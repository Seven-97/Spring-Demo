package com.seven.springhelloworldanno.service;


import com.seven.springhelloworldanno.dao.UserDaoImpl;
import com.seven.springhelloworldanno.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Seven
 */
@Service
public class UserServiceImpl {
    @Autowired
    private UserDaoImpl userDao;

    public List<User> findUserList() {
        return userDao.findUserList();
    }
}