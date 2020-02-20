package com.peng.blog.service;

import com.peng.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @Author:         PENG
* @CreateDate:     2020/2/12
*/
public interface TypeService {
    Type saveType(Type type);
    List<Type> listType();
    Page<Type> listTypeByPage(Pageable pageable);
    Page<Type> searchByPage(String name,Pageable pageable);
    Type findOne(int id);
    Type delOne(int id);
    List<Type> listForTop();
}
