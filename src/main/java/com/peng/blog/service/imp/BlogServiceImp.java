package com.peng.blog.service.imp;

import com.peng.blog.dao.BlogDao;
import com.peng.blog.dao.CommentDao;
import com.peng.blog.dao.TagDao;
import com.peng.blog.dao.TypeDao;
import com.peng.blog.entity.*;
import com.peng.blog.exception.NotFoundException;
import com.peng.blog.service.BlogService;
import com.peng.blog.utils.MyPage;
import com.peng.blog.utils.PageDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
* @Author:         PENG
* @CreateDate:     2020/2/13
*/
@Service
public class BlogServiceImp implements BlogService {

    private BlogDao blogDao;
    private TagDao tagDao;
    private TypeDao typeDao;
    private CommentDao commentDao;

    public BlogServiceImp() {
    }

    @Autowired
    public BlogServiceImp(BlogDao blogDao, TagDao tagDao, TypeDao typeDao, CommentDao commentDao) {
        this.blogDao = blogDao;
        this.tagDao = tagDao;
        this.typeDao = typeDao;
        this.commentDao = commentDao;
    }

    @Override
    public Page<Blog> listByPage(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Page<Blog> listByPageAndUser(Pageable pageable, User user) {
        return blogDao.listAllByUser(user.getId(),pageable);
    }

    @Transactional
    @Override
    public Page<Blog> searchByPage(Pageable pageable,Blog blog) {
        Specification<Blog> specification=new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                Predicate p=criteriaBuilder.like(root.get("title"),"%"+blog.getTitle()+"%");
                predicates.add(p);
                if(blog.getType().getId()!=null){
                    p=criteriaBuilder.equal(root.get("type").get("id"),blog.getType().getId());
                    predicates.add(p);
                }
                predicates.add(criteriaBuilder.equal(root.get("recommend_toggle"),blog.isRecommend_toggle()));
                predicates.add(criteriaBuilder.equal(root.get("publish_toggle"),blog.isPublish_toggle()));
                Predicate[] array=predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(array);
            }
        };
        return blogDao.findAll(specification,pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        try{

            if(blog.getId()==null){//添加博客时
                blog.setCreateTime(new Date());
            }else {
                Blog temp=blogDao.findOne(blog.getId());
                blog.setCreateTime(temp.getCreateTime());
                blog.setUser(temp.getUser());
                blog.setView_times(temp.getView_times());
            }
            blog.setLastUpdateTime(new Date());
            List<Tag> tags=tagDao.findAll(pareseTagIds((blog.getTagIds())));
            Type type=typeDao.findOne(blog.getType().getId());
            //绑定关系
            blog.setTags(tags);
            blog.setType(type);
            Blog temp=blogDao.save(blog);
            temp.setTagIds(blog.getTagIds());
            return temp;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Transactional
    @Override
    public Blog findOne(int id) {
        Blog blog=blogDao.findOne(id);
        blog.setTagIds(tagsToString(blog.getTags()));
        return blog;
    }

    @Transactional
    @Override
    public Blog delOne(int id) {
        Blog blog=blogDao.findOne(id);
        try{
            blogDao.delete(blog);
            return blog;
        }catch (Exception e){
            return null;
        }
    }

    @Transactional
    @Override
    public List<Blog> listForTop() {
        Pageable pageable=new PageRequest(0, PageDefinition.TOP_RECOMMEND_PAGE_SIZE,new Sort(Sort.Direction.DESC,"lastUpdateTime"));
        return blogDao.listTop(pageable);
    }

    @Override
    public Page<Blog> listAllPublishedBlogByPage(Pageable pageable) {
        return blogDao.listAllPublishedBlog(pageable);
    }

    @Transactional
    @Override
    public Page<Blog> globalSearch(String query, Pageable pageable) {
        return blogDao.listAllByTitleOrContentLike("%"+query+"%",pageable);
    }

    @Transactional
    @Override
    public Blog checkOne(int id,boolean update) throws NotFoundException {
        Blog blog=blogDao.findOne(id);
        if(blog==null || !blog.isPublish_toggle()) throw new NotFoundException("没有该博客");
        if (update) blog.setView_times(blog.getView_times()+1);
        return blog;
    }

    @Transactional
    @Override
    public List<Comment> getAllCommentsOfOneBlog(int id) throws NotFoundException {
        Blog blog=blogDao.findOne(id);
        if (blog==null)throw new NotFoundException("未找到博客");
        //查找一级评论，即没有父亲的。
        return commentDao.findByParentCommentAndBlog(null,blog);
    }

    @Override
    public Page<Blog> listBlogByTypeAndPage(int typeId, Pageable pageable) {
        return blogDao.listPageByType(typeId,pageable);
    }

    @Transactional
    @Override
    public List<Blog> listBlogByTagAndPage(int tagId, MyPage page) {
        page.setTotalElements(blogDao.totalElements(tagId));
        return blogDao.listPageByTag(tagId,page.getStartIndex(),page.getPageSize());
    }

    @Override
    public Map<Integer, List<Blog>> archive() {
//        List<Blog> allBlogs=blogDao.findAll(new Sort(Sort.Direction.DESC,"createTime"));
        List<Blog> allBlogs=blogDao.listPublishedAllAndOrderByCreateTime();
        Map<Integer, List<Blog>> map=new LinkedHashMap<>();
        map.put(allBlogs.size(),null);
        int currentYear=0;
        List<Blog> list=null;
        for (Blog blog:allBlogs){
            Calendar c=Calendar.getInstance();
            c.setTime(blog.getCreateTime());
            if(c.get(Calendar.YEAR)!=currentYear){
                currentYear=c.get(Calendar.YEAR);
                list=new ArrayList<>();
                map.put(currentYear,list);
            }
            list.add(blog);
        }
        return map;
    }

    public List<Integer> pareseTagIds(String tagIds){
        List<Integer> list=new ArrayList<>();
        if(tagIds!=null && !"".equals(tagIds)){
            String[] ids=tagIds.split(",");
            for(String s:ids)
                list.add(Integer.parseInt(s));
            return list;
        }
        return list;
    }

    String tagsToString(List<Tag> tags){
        if(tags!=null && tags.size()>0){
            StringBuffer buffer=new StringBuffer();
            for(Tag t:tags) buffer.append(t.getId() + ",");
            return buffer.substring(0,buffer.length()-1);
        }
        return "";
    }
}
