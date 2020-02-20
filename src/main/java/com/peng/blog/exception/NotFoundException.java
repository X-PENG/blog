package com.peng.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
//@ResponseStatus，指定抛出异常时对应的状态码！
//不加该注解，则抛出该异常时状态码为500。
//当抛出这个异常时，响应状态码就是404。然后会解析出404.html这个错误页面！
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
