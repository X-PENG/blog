package com.peng.blog;

import com.peng.blog.utils.MD5Utils;
import org.junit.Test;

public class T1 {
    @Test
    public void t1(){
        StringBuffer sb=new StringBuffer();
        sb.append("123456");
        //substring(start,end);参数都是索引位置，区间[start,end)，不包含end。
        System.out.println(sb.substring(0,sb.length()-1));
    }
    @Test
    public void t2(){
        System.out.println(MD5Utils.encode("123456"));
        System.out.println(MD5Utils.encode("123456").equals("e10adc3949ba59abbe56e057f20f883e"));
    }
    @Test
    public void t3(){
        System.out.println(MD5Utils.encode("123456"));
        System.out.println(MD5Utils.encode("123456").equals("e10adc3949ba59abbe56e057f20f883e"));
    }
}
