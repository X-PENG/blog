package com.peng.blog;

import com.peng.blog.entity.Type;
import com.peng.blog.service.TypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestType {
    @Autowired
    private TypeService typeService;

    @Transactional
    @Rollback(true)
    @Test
    public void t1(){
        Type type=new Type();
        type.setName("SpringMvc2");
        Type temp=type;
        try{
            type=typeService.saveType(type);
        }catch (Exception e){
//            type=null;
        }
        //总结：save()方法返回的type依然是传进去的type对象（引用相同），但是返回的type设置了id属性值。
        System.out.println(type.getId()!=null?type.getId().toString():"null");
        System.out.println(temp==type);//true。
        //切记：type和temp不是对象，是引用变量！！！
        //引用变量      引用内存中的一个对象！！！！
        //temp和type始终引用相同对象！！！
    }
}
