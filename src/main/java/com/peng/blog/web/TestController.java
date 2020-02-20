package com.peng.blog.web;

import com.peng.blog.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
@Controller
@RequestMapping("/test")
public class TestController {

    private final Logger logger= LoggerFactory.getLogger(TestController.class);
    @RequestMapping("/t1")
    @ResponseBody
    public String t1(String param) throws NotFoundException {
        logger.info("调用t1请求处理方法");
        logger.info("param={}",param);
        if("abc".equals(param)){
            logger.error("抛出NotFoundException");
            throw new NotFoundException("没有找到博客");
        }else if ("123".equals(param)){
            int i = 9 / 0;
        }
        return "t1";
    }
    @RequestMapping("/t2")
    @ResponseBody
    public String t2(String a,int b,boolean c) throws NotFoundException {
        logger.info("执行t2方法");
        return a+b+c;
    }
    @RequestMapping("/t3")
    @ResponseBody
    public String t3(int a) {
        logger.info("执行t2方法");
        int i = 9 / 0;
        return "t3->param_a="+a;
    }
}
