package com.peng.blog;


import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Tag;
import com.peng.blog.entity.Type;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestCopyProperties {
    @Test
    public void t1(){
        //只是对象引用集合！
        List<Tag> tags=new ArrayList<>();
        for(int i=0;i<5;i++){
            Tag tag=new Tag();
            tag.setId((i+1));
            tag.setName("tag"+i);
            tags.add(tag);
        }
        Blog b1=new Blog();
        b1.setId(100);
        b1.setAppreciate_toggle(true);
        b1.setComment_toggle(true);
        b1.setShare_toggle(true);
        b1.setRecommend_toggle(true);
        b1.setPublish_toggle(true);
        b1.setContent("123");
        b1.setCreateTime(new Date());
        b1.setLastUpdateTime(new Date());
        b1.setPicture_addr("http://");
        b1.setTitle("title");
        b1.setView_times(100);
        Type t=new Type();
        t.setName("类型");
        b1.setType(t);
        b1.setTags(tags);
        System.out.println("b1:\n"+b1);
        System.out.println("-----------------------------");
        Blog b2=new Blog();
        b2.setId(99);
        b2.setView_times(1000);
        b2.setContent("456");
        b2.setOriginal(2);
        System.out.println("b2:\n"+b2);
        System.out.println("-----------------------------");
        BeanUtils.copyProperties(b1,b2);
        System.out.println("b2:\n"+b2);
        System.out.println("------------------------");
        System.out.println(b1==b2);
    }
    /**
     * 实验结论：BeanUtils.copyProperties(a,b);
     * 可以将a引用的对象属性值“全部赋值给”b引用的对象的属性。
     * 避免一个个调用get、set方法！
     * 总结：用a引用的对象的属性值“全部覆盖”b引用的对象的属性。
     */


    //需求，不想a引用对象全部覆盖b引用对象，想忽略某些属性！
    //解决方案：copyProperties(Object source, Object target, String... ignoreProperties)。
    @Test
    public void t2() throws IllegalAccessException {
        //只是对象引用集合！
        List<Tag> tags=new ArrayList<>();
        for(int i=0;i<5;i++){
            Tag tag=new Tag();
            tag.setId((i+1));
            tag.setName("tag"+i);
            tags.add(tag);
        }
        Blog b1=new Blog();//模拟数据库中查出来的对象
        b1.setId(99);
        b1.setAppreciate_toggle(true);
        b1.setComment_toggle(true);
        b1.setShare_toggle(true);
        b1.setRecommend_toggle(true);
        b1.setPublish_toggle(true);
        b1.setContent("123");
        b1.setCreateTime(new Date());
        b1.setLastUpdateTime(new Date());
        b1.setPicture_addr("http://");
        b1.setTitle("title");
        b1.setView_times(100);
        Type t=new Type();
        t.setName("类型");
        b1.setType(t);
        b1.setTags(tags);
        System.out.println("b1:\n"+b1);
        System.out.println("-----------------------------");
        Blog b2=new Blog();//模拟更新时传过来的对象
        b2.setId(99);//id肯定一致
        b2.setView_times(1000);
        b2.setContent("456");
        b2.setOriginal(2);
        System.out.println("b2:\n"+b2);
        System.out.println("-----------------------------");
        BeanUtils.copyProperties(b2,b1,IgnorePropertiesFinder.lookup(b2));
        System.out.println("b1:\n"+b1);
        System.out.println("------------------------");
        System.out.println(b1==b2);
    }

    @Test
    public void t3() throws IllegalAccessException {
        Blog b=new Blog();
        System.out.println(Arrays.toString(IgnorePropertiesFinder.lookup(b)));

//        Class clazz=b.getClass().getSuperclass().getSuperclass();
        //一个类只有一个Class对象，是类加载的时候在内存中创建的。
//        if(clazz==Object.class) System.out.println(clazz==Object.class);

    }


    static class IgnorePropertiesFinder{
        //成员内部类不能声明static成员。
        //原因：static成员不依赖于任何对象，而成员内部类本身依赖于外部类对象，导致static成员也依赖外部类对象，错误！！！
        public static String[] lookup(Object object) throws IllegalAccessException {
            List<String> ignoreProperties=new ArrayList<>();
            Class clazz=object.getClass();
            find(ignoreProperties,object,clazz);
            return ignoreProperties.toArray(new String[ignoreProperties.size()]);
        }

        //递归是先递推（自己调用自己，解决同一种问题！），再回归。“自己调用自己”。递归是将规模为n的问题降阶为n-1甚至更低的相同问题。
        public static void find(List<String> list,Object object,Class clazz) throws IllegalAccessException {
            if(clazz.getSuperclass()!=Object.class){//有父类
                find(list,object,clazz.getSuperclass());
            }
            //递归方法的核心，即用于处理业务的。
            for(Field field:clazz.getDeclaredFields()){
                field.setAccessible(true);//设置可访问！
                if(field.get(object)==null){
                    list.add(field.getName());
                }
            }
        }
    }
}
