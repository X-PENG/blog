package com.peng.blog.dao;

import com.peng.blog.entity.Tag;
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
public interface TagDao extends JpaRepository<Tag,Integer> {
    Page<Tag> findByNameLike(String name, Pageable pageable);

    @Query("select t from Tag t")
    List<Tag> listTop(Pageable pageable);
}
