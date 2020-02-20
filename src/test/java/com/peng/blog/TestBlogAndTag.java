package com.peng.blog;

import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Tag;
import com.peng.blog.service.BlogService;
import com.peng.blog.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @Author:         PENG
* @CreateDate:     2020/2/14
*/
//测试blog和tag多对多关系，数据库为：blogtest2
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBlogAndTag {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    //测试保存Blog时日期不格式化
    @Test
    public void t1(){
        Blog b=new Blog();
        b.setCreateTime(new Date());
        b.setLastUpdateTime(new Date());
        b.setOriginal(0);
        b.setPicture_addr("http://123.com");
        b.setTitle("第1篇博客");
        b.setContent("这是我的第一篇博客");
        blogService.saveBlog(b);
    }
    //测试保存Blog时日期格式化
    @Test
    public void t2() throws ParseException {
        Blog b=new Blog();
        b.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2020/02/14 12:40:39"));
        b.setLastUpdateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2020/02/14 12:40:40"));
        b.setOriginal(0);
        b.setPicture_addr("http://123.com");
        b.setTitle("第2篇博客");
        b.setContent("这是我的第二篇博客");
        blogService.saveBlog(b);
    }
    //测试Blog的setTags()方法进行关系绑定
    @Transactional
    @Rollback(false)
    @Test
    public void t3(){
        List<Tag> tags=new ArrayList<>();
        tags.add(tagService.findOne(1));
        tags.add(tagService.findOne(2));
        Blog b=new Blog();
        b.setCreateTime(new Date());
        b.setLastUpdateTime(new Date());
        b.setOriginal(0);
        b.setPicture_addr("http://123.com");
        b.setTitle("第3篇博客");
        b.setContent("这是我的第三篇博客");
        b.setTags(tags);
        blogService.saveBlog(b);
    }
    //测试Tag的setBlogs()方法进行关系绑定，不会关系绑定。
    @Transactional
    @Rollback(true)
    @Test
    public void t4(){
        List<Blog> blogs=new ArrayList<>();
        blogs.add(blogService.findOne(1));
        blogs.add(blogService.findOne(2));
        Tag tag=tagService.findOne(9);
        tag.setBlogs(blogs);//不会进行关系绑定
        tagService.saveTag(tag);
    }
}
