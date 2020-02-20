package com.peng.blog.dao;

import com.peng.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Author:         PENG
* @CreateDate:     2020/2/12
*/
@Repository
public interface TypeDao extends JpaRepository<Type,Integer> {
    Page<Type> findByNameLike(String name, Pageable pageable);

    /**
     *
     * 查询每个分类有多少个博客的SQL语句：
     * select type_id,count(*) as blog_num from t_blog group by type_id order by blog_num desc
     *
     * 查询出博客数大于0的分类信息、携带博客数，SQL语句如下：
     * SELECT m.*,n.blog_num FROM t_type as m left JOIN (select type_id,count(*) as blog_num from t_blog group by type_id) as n
     * on m.id=n.type_id WHERE n.blog_num is not NULL ORDER BY n.blog_num desc LIMIT 0,5
     *
     */
    @Query("select t from Type t")
    List<Type> listTop(Pageable pageable);


}
