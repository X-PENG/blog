package com.peng.blog.BlogAndComment;

import com.peng.blog.utils.PhotoNameUtils;
import org.junit.Test;

public class TestUUID {
    @Test
    public void t1(){
        System.out.println(PhotoNameUtils.GetId());
    }
    @Test
    public void t2(){
        String str="就去偶就去闻鸡起舞.jpg";
        System.out.println(str.substring(str.indexOf(".")));
    }
    @Test
    public void t3(){
        String str="D:/workspaces/ideaWebWorkSpace/blog/target/classes/static";
        System.out.println(str.indexOf("/target"));
        System.out.println(str.substring(0,str.indexOf("target")));
    }
}
