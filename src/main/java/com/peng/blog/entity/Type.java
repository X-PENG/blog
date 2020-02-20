package com.peng.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
@Entity
@Table(name="t_type")
public class Type extends EntityID implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false,unique = true)
    private String name;
    @OneToMany(targetEntity = Blog.class,mappedBy = "type")
    private List<Blog> blogs=new ArrayList<>();

    public Type() {
    }
    public Type(String name) {
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }



    @Override
    public String toString() {
        return "Type{" +
                "id='" + id + '\'' +
                ", name=" + name +
                '}';
    }
}
