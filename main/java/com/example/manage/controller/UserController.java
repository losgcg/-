package com.example.manage.controller;


import com.example.manage.pojo.User;
import com.example.manage.service.UserService;
import com.example.manage.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class UserController {
    @Autowired(required=true)
    private UserService userService;
    @Autowired(required=true)
    private  ResultMap resultMap;

    @RequestMapping("/toLogin")
    public  String toLogin(){
        return "login";
    }



    @RequestMapping("/login")
    public String Login(User user, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User users = userService.queryByUsernameAndPassword(user.getUsername(),user.getPassword());
       if(users == null){
           //重新跳转到登录界面  并且给出相应的提示
           //转发来进行跳转页面
           model.addAttribute("用户名或者密码输入错误");
           return "login";
        }else if (users.getRole() == 1){
           //跳转到管理员界面
           request.setAttribute("admin",users);
           session.setAttribute("admin",users);
           return "adminShow";
       }else {
           request.getSession().setAttribute("user",users);
           //跳转到顾客
           return "customerShow";
       }

    }
//    @RequestMapping("/login")
//    @ResponseBody
//    public ResultMap login(String username,String password,HttpSession session) {
//        try {
//            userService.queryByUsernameAndPassword(username,password,session);
//            resultMap.setStatus(true);
//        } catch (Exception e) {
//            resultMap.setStatus(false);
//            resultMap.setMessage(e.getMessage());
//        }
//        return resultMap;
//    }



}
