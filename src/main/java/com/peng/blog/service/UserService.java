package com.peng.blog.service;

import com.peng.blog.entity.User;

/**
* @Author:         PENG
* @CreateDate:     2020/2/11
*/
public interface UserService {
    User checkUser(String username, String password);
}
