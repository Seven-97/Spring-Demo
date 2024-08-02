package com.seven.springmybatis.service;

import com.seven.springmybatis.entity.User;
import com.seven.springmybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Seven
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public List<User> queryAll(int offset, int limit){
        return userMapper.queryAll(offset, limit);
    }
}
