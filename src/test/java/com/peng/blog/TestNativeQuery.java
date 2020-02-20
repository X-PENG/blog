package com.peng.blog;

import com.peng.blog.dao.BlogDao;
import com.peng.blog.entity.Blog;
import com.peng.blog.utils.MyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestNativeQuery {

    @Autowired
    private BlogDao blogDao;

    @Transactional
    @Test
    public void t1(){
        int tagId=17;
        MyPage myPage=new MyPage();
        myPage.setPage(3);
        myPage.setPageSize(1);
        List<Blog> list=blogDao.listPageByTag(tagId,myPage.getPage(),myPage.getPageSize());
        System.out.println(list.size());
        for (Blog blog:list){
            System.out.println("--------------");
            System.out.println("blog.id--->"+blog.getId());
            System.out.println(blog.getType());
            System.out.println(blog.getUser());
        }
        System.out.println("===============================================");
        int totalElements=blogDao.totalElements(tagId);
        System.out.println("totalElements="+totalElements);
        myPage.setTotalElements(totalElements);
        System.out.println(myPage.getTotalPages());
        System.out.println(myPage.isFirst());
        System.out.println(myPage.isLast());
    }
}
