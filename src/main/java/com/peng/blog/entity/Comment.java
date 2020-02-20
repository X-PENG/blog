package com.peng.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
@Entity
@Table(name = "t_comment")
public class Comment extends EntityID implements Serializable {
    private static final long serialVersionUID = 1L;

    private String content;
    private String nickname;
    private String mail;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date publish_time;
    @ManyToOne
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    @JsonIgnore
    private Comment parentComment;
    @OneToMany(targetEntity = Comment.class,mappedBy = "parentComment")
//    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    private List<Comment> childs=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "blog_id",referencedColumnName = "id",nullable = false)
    @JsonIgnore
    private Blog blog;

    public Comment() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public List<Comment> getChilds() {
        return childs;
    }

    public void setChilds(List<Comment> childs) {
        this.childs = childs;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mail='" + mail + '\'' +
                ", avatar='" + avatar + '\'' +
                ", publish_time=" + publish_time +
                ", childs=" + childs +
                '}';
    }
}
