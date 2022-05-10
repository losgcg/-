package com.example.manage.service;

import com.example.manage.pojo.User;

import javax.servlet.http.HttpSession;


public interface UserService {
   // public void queryByUsernameAndPassword(String username, String password, HttpSession session);


    public User queryByUsernameAndPassword(String username, String password);
}
