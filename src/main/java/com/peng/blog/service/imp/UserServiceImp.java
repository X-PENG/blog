package com.peng.blog.service.imp;

import com.peng.blog.dao.UserDao;
import com.peng.blog.entity.User;
import com.peng.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @Author:         PENG
* @CreateDate:     2020/2/11
*/
@Service
public class UserServiceImp implements UserService {

//    @Autowired//属性field注入不被推荐
    private UserDao userDao;

    public UserServiceImp() {
    }

    //使用构造器注入。强制依赖
    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User checkUser(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }
}
