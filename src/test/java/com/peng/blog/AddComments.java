package com.peng.blog;

import com.peng.blog.dao.BlogDao;
import com.peng.blog.dao.CommentDao;
import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddComments {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private BlogDao blogDao;


    //增加1级评论
    @Transactional
    @Rollback(true)
    @Test
    public void t1(){
        Blog b=blogDao.findOne(30);
        Comment comment=new Comment();
        comment.setBlog(b);
        comment.setPublish_time(new Date());
        comment.setNickname("BBBB");
        comment.setMail("231@qq.com");
        comment.setContent("我是一级评论，代号c2");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        commentDao.save(comment);
        comment=new Comment();
        comment.setBlog(b);
        comment.setPublish_time(new Date());
        comment.setNickname("CCCC");
        comment.setMail("231@qq.com");
        comment.setContent("我是一级评论，代号c3");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        commentDao.save(comment);
        comment=new Comment();
        comment.setBlog(b);
        comment.setPublish_time(new Date());
        comment.setNickname("DDDD");
        comment.setMail("231@qq.com");
        comment.setContent("我是一级评论，代号c4");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        commentDao.save(comment);
    }

    //增加2级评论
    @Transactional
    @Rollback(true)
    @Test
    public void t2(){
        Comment parent=commentDao.findOne(2);
        Comment comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("海王");
        comment.setMail("231@qq.com");
        comment.setContent("我是二级评论，代号c1_1");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
        comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("毒液");
        comment.setMail("231@qq.com");
        comment.setContent("我是二级评论，代号c1_2");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
        parent=commentDao.findOne(4);
        comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("蜘蛛侠");
        comment.setMail("231@qq.com");
        comment.setContent("我是二级评论，代号c3_1");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
        comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("钢铁侠");
        comment.setMail("231@qq.com");
        comment.setContent("我是二级评论，代号c3_2");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
    }
    //增加3级评论
    @Transactional
    @Rollback(true)
    @Test
    public void t3(){
        Comment parent=commentDao.findOne(6);
        Comment comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("狗符咒");
        comment.setMail("456@qq.com");
        comment.setContent("我是三级评论，代号c1_1_1");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
        comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("兔符咒");
        comment.setMail("789@qq.com");
        comment.setContent("我是三级评论，代号c1_2_1");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
        parent=commentDao.findOne(8);
        comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("小玉");
        comment.setMail("231@qq.com");
        comment.setContent("我是三级评论，代号c3_1_1");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
        comment=new Comment();
        comment.setBlog(parent.getBlog());
        comment.setPublish_time(new Date());
        comment.setNickname("老爹");
        comment.setMail("231@qq.com");
        comment.setContent("我是三级评论，代号c3_2_1");
        comment.setAvatar("https://i.picsum.photos/id/1005/2515/1830.jpg");
        comment.setParentComment(parent);
        commentDao.save(comment);
    }
}
