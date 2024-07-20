package com.seven.springhelloworldconfig.service;


import com.seven.springhelloworldconfig.pojo.User;
import com.seven.springhelloworldconfig.dao.UserDaoImpl;

import java.util.List;

/**
 * @author Seven
 */
public class UserServiceImpl {
    private UserDaoImpl userDao;

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public List<User> findUserList() {
        return userDao.findUserList();
    }
}
