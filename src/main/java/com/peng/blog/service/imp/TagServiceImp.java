package com.peng.blog.service.imp;

import com.peng.blog.dao.TagDao;
import com.peng.blog.entity.Tag;
import com.peng.blog.service.TagService;
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
public class TagServiceImp implements TagService {

    private TagDao tagDao;

    public TagServiceImp() {
    }

    @Autowired
    public TagServiceImp(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag saveTag(Tag tag) {
        try{
            return tagDao.save(tag);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Page<Tag> listTagByPage(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag delOne(int id) {
        try{
            Tag tag=tagDao.findOne(id);
            tagDao.delete(tag);
            return tag;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Tag findOne(int id) {
        return tagDao.findOne(id);
    }

    @Transactional
    @Override
    public Page<Tag> searchByPage(String name, Pageable pageable) {
        return tagDao.findByNameLike("%"+name+"%",pageable);
    }

    @Override
    public List<Tag> listTag() {
        List<Tag> tags=tagDao.findAll();
        for(Tag t:tags){
            t.getBlogs().removeIf(blog -> !blog.isPublish_toggle());
        }
        return tags;
    }

    @Transactional
    @Override
    public List<Tag> listForTop() {
        Pageable pageable=new PageRequest(0, PageDefinition.TOP_TAG_PAGE_SIZE,new Sort(Sort.Direction.DESC,"blogs.size"));
        List<Tag> tags=tagDao.listTop(pageable);
        for (Tag t:tags){
            t.getBlogs().removeIf(blog -> !blog.isPublish_toggle());
        }
        return tags;
    }
}
