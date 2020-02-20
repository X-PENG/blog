package com.peng.blog.service.imp;

import com.peng.blog.dao.TypeDao;
import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Type;
import com.peng.blog.service.TypeService;
import com.peng.blog.utils.PageDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
* @Author:         PENG
* @CreateDate:     2020/2/12
*/
@Service
public class TypeServiceImp implements TypeService {
    private TypeDao typeDao;

    public TypeServiceImp() {
    }

    @Autowired
    public TypeServiceImp(TypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Override
    public Type saveType(Type type) {
        try {
            return typeDao.save(type);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Type> listType() {
        List<Type> types=typeDao.findAll();
        for(Type t:types){
            t.getBlogs().removeIf(blog -> !blog.isPublish_toggle());
        }
        return types;
    }

    @Override
    public Page<Type> listTypeByPage(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Type> searchByPage(String name, Pageable pageable) {
        return typeDao.findByNameLike("%"+name+"%",pageable);
    }

    @Override
    public Type findOne(int id){
        return typeDao.findOne(id);
    }

    @Transactional
    @Override
    public Type delOne(int id) {
        try{
            Type type=typeDao.findOne(id);
            typeDao.delete(type);
            return type;
        }catch (Exception e){
            return null;
        }
    }


    @Transactional
    @Override
    public List<Type> listForTop() {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0, PageDefinition.TOP_TYPE_PAGE_SIZE,sort);
        List<Type> types=typeDao.listTop(pageable);
        //去掉未发布的博客
        for(Type t:types){
            List<Blog> temp=t.getBlogs();
            temp.removeIf(b -> !b.isPublish_toggle());
        }
        //去掉博客数为0的
        types.removeIf(type -> type.getBlogs().size() < 1);
        return types;
    }
}
