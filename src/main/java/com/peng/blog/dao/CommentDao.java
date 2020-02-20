package com.peng.blog.dao;

import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {
    //查询指定博客和指定孩子的评论
    List<Comment> findByChildsAndBlog(Set<Comment> childs, Blog blog);
    //查询指定博客和指定父亲的评论
    List<Comment> findByParentCommentAndBlog(Comment parentComment,Blog blog);
}
