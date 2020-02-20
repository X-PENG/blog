package com.peng.blog.service;

import com.peng.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);
    Page<Tag> listTagByPage(Pageable pageable);
    Tag delOne(int id);
    Tag findOne(int id);
    Page<Tag> searchByPage(String name,Pageable pageable);
    List<Tag> listTag();
    List<Tag> listForTop();
}
