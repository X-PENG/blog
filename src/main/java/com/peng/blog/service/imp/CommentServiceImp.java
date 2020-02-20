package com.peng.blog.service.imp;

import com.peng.blog.dao.BlogDao;
import com.peng.blog.dao.CommentDao;
import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Comment;
import com.peng.blog.service.CommentService;
import com.peng.blog.utils.DefaultUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class CommentServiceImp implements CommentService {

    private CommentDao commentDao;
    private BlogDao blogDao;

    public CommentServiceImp() {
    }

    @Autowired
    public CommentServiceImp(CommentDao commentDao, BlogDao blogDao) {
        this.commentDao = commentDao;
        this.blogDao = blogDao;
    }

    @Transactional
    @Override
    public Comment saveChildComment(int parentId, Comment comment) {
        Comment parentComment=commentDao.findOne(parentId);
        comment.setBlog(parentComment.getBlog());
        comment.setPublish_time(new Date());
        comment.setParentComment(parentComment);
        if (comment.getAvatar()==null || comment.getAvatar().equals("") ){
            comment.setAvatar(DefaultUrl.COMMENT_AVATAR);
        }
        comment=commentDao.save(comment);
        return comment;
    }

    @Transactional
    @Override
    public Comment saveFirstComment(int blogId, Comment comment) {
        Blog blog=blogDao.findOne(blogId);
        comment.setBlog(blog);
        comment.setPublish_time(new Date());
        if (comment.getAvatar()==null || comment.getAvatar().equals("") ){
            comment.setAvatar(DefaultUrl.COMMENT_AVATAR);
        }
        comment=commentDao.save(comment);
        return comment;
    }
}
