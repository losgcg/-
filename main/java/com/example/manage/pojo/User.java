package com.example.manage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;
    private int role=1;


    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
}
