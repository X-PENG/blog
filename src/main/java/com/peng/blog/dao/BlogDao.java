package com.peng.blog.dao;

import com.peng.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
@Repository
public interface BlogDao extends JpaRepository<Blog,Integer>, JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b where b.publish_toggle=true and b.recommend_toggle=true")
    List<Blog> listTop(Pageable pageable);

    @Query("select b from Blog b where b.user.id=:id")
    Page<Blog> listAllByUser(@Param("id") Integer id, Pageable pageable);

    @Query("select b from Blog b where b.publish_toggle=true")
    Page<Blog> listAllPublishedBlog(Pageable pageable);

    @Query("select b from Blog b where b.publish_toggle=true and (b.title like :query or b.content like :query)")
    Page<Blog> listAllByTitleOrContentLike(@Param("query") String queryStr,Pageable pageable);

    @Query("select b from Blog b where b.publish_toggle=true and b.type.id=:id")
    Page<Blog> listPageByType(@Param("id")int typeId,Pageable pageable);

    @Query(nativeQuery = true,value = "select * from t_blog WHERE publish_toggle=true and id in (SELECT blog_id FROM t_blog_tag WHERE tag_id = :tagId) ORDER BY create_time DESC LIMIT :start,:pageSize")
    List<Blog> listPageByTag(@Param("tagId")int tagId, @Param("start")int startIndex, @Param("pageSize")int pageSize);

    @Query(nativeQuery = true,value = "select count(*) from t_blog WHERE publish_toggle=true and id in (SELECT blog_id FROM t_blog_tag WHERE tag_id = :tagId)")
    int totalElements(@Param("tagId")int tagId);
    @Query("select b from Blog b where b.publish_toggle=true order by b.createTime DESC")
    List<Blog> listPublishedAllAndOrderByCreateTime();
}
