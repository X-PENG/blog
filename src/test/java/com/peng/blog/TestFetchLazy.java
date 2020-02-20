package com.peng.blog;

import com.peng.blog.dao.BlogDao;
import com.peng.blog.dao.TypeDao;
import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Type;
import com.peng.blog.service.BlogService;
import com.peng.blog.service.TypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFetchLazy {
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    //经测试，content和description的确是懒加载。
    @Test
    public void t1(){
        Blog b=blogDao.findOne(24);//通过，不是延迟加载。
//        Blog b=blogDao.getOne(24);//不通过，是延迟加载。
        System.out.println("---------------------");
//        System.out.println(b);
        System.out.println("=============================");
        System.out.println(b.getContent());
        System.out.println(b.getDescription());
//        System.out.println(b.getTags().size());//对象集合一定是懒加载！！！
    }

    @Transactional
    @Test
    public void t2(){
        Blog b=blogDao.getOne(23);
        System.out.println("---------------------");
//        System.out.println(b);
        System.out.println("=============================");
        System.out.println(b.getContent());
        System.out.println(b.getDescription());
    }

    //猜想：是service的原因。为啥service方法执行完了，还能懒加载。
    @Test
    public void t3(){
        Blog b=blogService.findOne(23);
        System.out.println("---------------------");
//        System.out.println(b);
        System.out.println("=============================");
        System.out.println(b.getContent());
        System.out.println(b.getDescription());
    }

//    @Transactional
//    @Test
//    public void t4(){
//        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
//        Pageable pageable=new PageRequest(0,6,sort);
//        List<Type> types=typeService.listByPageAndSort(pageable);
//        for (Type t:types){
//            System.out.println(t);
//            System.out.println("size-->"+t.getBlogs().size());
//        }
//    }
    @Test
    public void t5(){
        Type t=typeDao.findOne(12);
            System.out.println(t);
            System.out.println("size-->"+t.getBlogs().size());//blogs懒加载异常
    }

}
