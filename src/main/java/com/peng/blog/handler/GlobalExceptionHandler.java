package com.peng.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e,HttpServletRequest request) throws Exception{
        logger.info("调用handleException方法");
        logger.error("抛出异常:{}",e.toString());
        e.printStackTrace();
//        logger.info("错误状态码：{}",Integer.parseInt(request.getAttribute("javax.servlet.error.status_code").toString()));
        if(e.getClass().isAnnotationPresent(ResponseStatus.class)){
            logger.info("{}存在ResponseStatus注解",e.getClass().getName());
            Class clazz=e.getClass();
            //注解是标注在类上面的，而不是类对象！
            //注解是给程序看的。注释是给程序员看的。
            ResponseStatus responseStatus = (ResponseStatus) clazz.getAnnotation(ResponseStatus.class);

//            logger.info("触发/error请求，由SpringBoot处理异常");
//            return "forward:/error";//此时，响应状态码为200。回去找error/200这个错误视图
            if(responseStatus.value()== HttpStatus.NOT_FOUND){
                logger.error("状态码{}，继续抛出异常由SpringBoot自己处理",responseStatus.value());
                throw e;//继续抛出异常，就会由SpringBoot来处理异常。响应状态码是该异常绑定的状态码：404
            }
        }
//        throw  e;
        return "error/error";
    }
}
