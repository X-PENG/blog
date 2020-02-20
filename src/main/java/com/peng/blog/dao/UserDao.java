package com.peng.blog.dao;

import com.peng.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
* @Author:         PENG
* @CreateDate:     2020/2/11
*/
@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsernameAndPassword(String username,String password);
}
