package com.seven.springhelloworldxml.service;

import com.seven.springhelloworldxml.dao.UserDaoImpl;
import com.seven.springhelloworldxml.pojo.User;

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
