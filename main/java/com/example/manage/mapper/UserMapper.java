package com.example.manage.mapper;

import com.example.manage.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //查询
    //public User queryByUsernameAndPassword(User user);

    public User queryByUsernameAndPassword(String username, String password);
}
