package com.peng.blog;

import com.peng.blog.enums.BlogCopyrightSource;
import org.junit.Test;

public class TestMyEnum {
    @Test
    public void t1(){
        int ordinal=0;
        System.out.println(BlogCopyrightSource.getDescriptionByOrdinal(ordinal));
        BlogCopyrightSource type=BlogCopyrightSource.ORIGINAL;
        System.out.println(type);
        System.out.println(type.ordinal());
    }
}
