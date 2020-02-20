//package com.peng.blog;
//
//import com.peng.blog.dao.BlogDao;
//import com.peng.blog.dao.CommentDao;
//import com.peng.blog.entity.Blog;
//import com.peng.blog.entity.Comment;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
///**
// * 测试Blog和Comment表。数据库为blogtest。
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BlogApplication.class)
//public class BlogApplicationTests {
//    private  static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    @Autowired
//    private BlogDao blogDao;
//
//    @Autowired
//    private CommentDao commentDao;
//
//    @Test
//    public void contextLoads() {
//        boolean a=true;
//        System.out.println("peng"+1+a);
//    }
//
//    @Test
//    public void t1(){
//        if(blogDao!=null && commentDao!=null)
//            System.out.println("success");
//    }
//
//    //给Blog表插入记录
//    @Transactional
//    @Rollback(value = false)
//    @Test
//    public void t2() throws Exception{
//        Blog b1=new Blog();
//        b1.setTitle("Blog4");
//        b1.setContent("这是我的第四篇博客");
//        b1.setAppreciate_toggle(false);
//        b1.setComment_toggle(false);
//        b1.setShare_toggle(false);
//        b1.setRecommend_toggle(true);
//        b1.setPicture_addr("https://i.picsum.photos/id/0/5616/3744.jpg");
//        b1.setOriginal(0);
//        b1.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-02-10 13:40"));
//        b1.setLast_update_time(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2020-02-10 13:40"));
//        b1=blogDao.save(b1);
//        System.out.println("id="+b1.getId().toString());
//    }
//
//    //查询一条Blog记录
//    //问题：为什么必须立即加载？！？为什么懒加载异常！？
//    //因为执行完findOne，session已经关闭了！！！
//    //session关闭后再getComments，触发懒加载就会异常！！！
//    //解决方案：使用@Transaction注解。使该方法所以操作都在一个事务内！只有方法执行完后（事务提交），session才关闭。
//    @Transactional
//    @Test
//    public void t3(){
//        Blog blog=blogDao.findOne(1);
//        System.out.println(blog);
//        System.out.println("---------------------");
//        //懒加载，调用getter方法才执行select语句查找评论
//        //否则，在findOne时会t_blog个t_comment执行左外连接查询
//        Set<Comment> comments=blog.getComments();
//        System.out.println("size="+comments.size());
//        System.out.println("------------------------------");
////        System.out.println(comments);
//        for(Comment c:comments){
//            System.out.println(c);
//        }
//    }
//
//
//    //添加一条Comment且与一个Blog绑定关系。Blog作为关系维护端
//    @Transactional
//    @Rollback(true)
//    @Test
//    public void t4() throws Exception{
//        Blog blog=blogDao.findOne(4);
//        Comment c1=new Comment();
//        c1.setHead_photo("http://");
//        c1.setNickname("John");
//        c1.setMail("231@qq.com");
//        c1.setContent("这是第3条评论");
//        c1.setPublish_time(sdf.parse(sdf.format(new Date())));
//        //由于Comment不是关系comment_blog的关系维护方，所以不能setBlog()来绑定关系
//        blog.getComments().add(c1);//性能很低
//        commentDao.save(c1);//保存之前必须先绑定关系，保证blog_id不为null。
//        System.out.println("comment_id="+c1.getId());
//    }
//
//
////-------------------------------以下测试，Blog和Comment变成双向1对n。且Comment维护关系。-------------------
//    //问题：由于blog.getComments().add(c1);这样绑定关系性能很低（要进行两次查询）。
//    //改善：让Comment作为关系维护方
//    //注意：让多方作为关系维护方，此时一方Blog就不是关系维护方，不能blog.getComments().add(c1);来绑定关系！
//
//    //Comment作为关系维护端。添加一条Comment且与一个Blog绑定关系
//    @Transactional
//    @Rollback(false)
//    @Test
//    public void t5() throws Exception{
//        Blog blog=blogDao.findOne(1);
//        Comment c1=new Comment();
//        c1.setHead_photo("http://");
//        c1.setNickname("John");
//        c1.setMail("231@qq.com");
//        c1.setContent("这是第2条评论");
//        c1.setPublish_time(sdf.parse(sdf.format(new Date())));
//        c1.setBlog(blog);
//        c1=commentDao.save(c1);
//        System.out.println("comment_id="+c1.getId().toString());
//    }
//
//    //给评论添加父评论。
//    //分析：由于一方（父评论）是关系维护方，只能parent.getChilds().add(c1)来绑定关系！
//    @Transactional
//    @Rollback(false)
//    @Test
//    public void t6() throws Exception{
//        Comment parent=commentDao.findOne(1);
//        Blog blog=blogDao.findOne(1);
//        Comment c1=new Comment();
//        c1.setHead_photo("http://");
//        c1.setNickname("Kasa");
//        c1.setMail("862@qq.com");
//        c1.setContent("你讲的真好");
//        c1.setPublish_time(sdf.parse(sdf.format(new Date())));
//        c1.setBlog(blog);
//        c1=commentDao.save(c1);
//        Comment c2=new Comment();
//        c2.setHead_photo("http://");
//        c2.setNickname("Kasa");
//        c2.setMail("862@qq.com");
//        c2.setContent("你讲的真好");
//        c2.setPublish_time(sdf.parse(sdf.format(new Date())));
//        c2.setBlog(blog);
//        c2=commentDao.save(c2);
//        System.out.println("comment_id="+c2.getId().toString());
//        if(blog.getId().intValue()==parent.getBlog().getId().intValue())
//            parent.getChilds().addAll(Arrays.asList(c1,c2));
//    }
//
//    //查询指定博客且没有孩子的评论
//    @Transactional
//    @Test
//    public void t7(){
//        List<Comment> comments=commentDao.findByChildsAndBlog(null,blogDao.findOne(1));
//        System.out.println("size="+comments.size());
//        for (Comment c:comments)
//            System.out.println(c);
//    }
//    //查询指定博客且没有父亲的评论
//    @Transactional
//    @Test
//    public void t8(){
//        List<Comment> comments=commentDao.findByParentCommentAndBlog(null,blogDao.findOne(1));
//        System.out.println("size="+comments.size());
//        //打印Comment时要getChilds()就会执行查询，找到并打印孩子再getChilds又会执行查询,找到并打印孙子又getChilds又会执行查询......。
//        for (Comment c:comments)
//            System.out.println(c);
//    }
//
//
//    //添加三级评论
//    @Test
//    @Transactional
//    @Rollback(false)
//    public void t9() throws Exception{
//        Comment parent=commentDao.findOne(12);
//        Comment child=new Comment();
//        child.setHead_photo("http://");
//        child.setNickname("Sara");
//        child.setMail("278@qq.com");
//        child.setContent("我也赞同二楼");
//        child.setPublish_time(sdf.parse(sdf.format(new Date())));
//        child.setBlog(parent.getBlog());
//        child.setParentComment(parent);
//        commentDao.save(child);
//        child=new Comment();
//        child.setHead_photo("http://");
//        child.setNickname("Tom");
//        child.setMail("376@qq.com");
//        child.setContent("666666");
//        child.setPublish_time(sdf.parse(sdf.format(new Date())));
//        child.setBlog(parent.getBlog());
//        child.setParentComment(parent);
//        commentDao.save(child);
//
//    }
//
//}
