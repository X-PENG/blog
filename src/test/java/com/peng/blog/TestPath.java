package com.peng.blog;

import org.junit.Test;

import java.io.File;

public class TestPath {
    @Test
    public void t1(){
        String basePath="D:/我的云服务器/blog-0.0.1-SNAPSHOT.jar";
        File file1=new File(basePath);
        System.out.println(file1.exists());
        String extPath="D:/我的云服务器/blog-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/static";
        File file2=new File(extPath);
        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.exists());
    }
}
