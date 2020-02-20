package com.peng.blog.service;

import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Comment;
import com.peng.blog.entity.User;
import com.peng.blog.exception.NotFoundException;
import com.peng.blog.utils.MyPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
* @Author:         PENG
* @CreateDate:     2020/2/13
*/
public interface BlogService {
    Page<Blog> listByPage(Pageable pageable);
    Page<Blog> listByPageAndUser(Pageable pageable, User user);
    Page<Blog> searchByPage(Pageable pageable,Blog blog);
    Blog saveBlog(Blog blog);
    Blog findOne(int id);
    Blog delOne(int id);
    List<Blog> listForTop();
    Page<Blog> listAllPublishedBlogByPage(Pageable pageable);
    //前台调用
    Page<Blog> globalSearch(String query,Pageable pageable);
    Blog checkOne(int id,boolean update) throws NotFoundException;
    //获取所有评论
    List<Comment> getAllCommentsOfOneBlog(int id) throws NotFoundException;
    //根据type分页获取Blog
    Page<Blog> listBlogByTypeAndPage(int typeId,Pageable pageable);
    //根据tag分页获取Blog
    List<Blog> listBlogByTagAndPage(int tagId, MyPage page);
    //归档
    Map<Integer,List<Blog>> archive();
}
