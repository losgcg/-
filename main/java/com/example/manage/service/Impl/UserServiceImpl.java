package com.example.manage.service.Impl;

import com.example.manage.mapper.UserMapper;
import com.example.manage.pojo.User;
import com.example.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required=true)
    UserMapper userMapper;

//    @Override
//    public void queryByUsernameAndPassword(String username, String password, HttpSession session) {
//        if(username.equals("")) {
//            throw new RuntimeException("用户名不能为空！");
//        }
//        if(password.equals("")) {
//            throw new RuntimeException("密码不能为空！");
//        }
//        User user = new User(username,password);
//        user = userDao.queryByUsernameAndPassword(user);
//        if(user == null) {
//            throw new RuntimeException("用户名或密码输入有误！");
//        }else {
//            session.setAttribute("user", user);
//        }
//    }

    @Override
    public User queryByUsernameAndPassword(String username, String password) {
        return userMapper.queryByUsernameAndPassword(username,password);
    }


}
