package com.peng.blog.service;

import com.peng.blog.entity.Comment;

public interface CommentService {
    Comment saveChildComment(int parentId,Comment comment);
    Comment saveFirstComment(int blogId,Comment comment);
}
