package com.seven.springmybatis.mapper;

import com.seven.springmybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Seven
 */
public interface UserMapper {

    List<User> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
