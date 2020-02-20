package com.peng.blog.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/
@Entity
@Table(name = "t_blog")
public class Blog extends EntityID implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "标题不能为空")
    @Column(nullable = false,length = 100)
    private String title;
    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Integer original;
    private boolean recommend_toggle;
    private boolean share_toggle;
    private boolean appreciate_toggle;
    private boolean comment_toggle;
    private boolean publish_toggle;
    private String picture_addr;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
    private int view_times;
//    @OneToMany(targetEntity = Comment.class,fetch = FetchType.EAGER)
    @OneToMany(targetEntity = Comment.class,mappedBy = "blog",cascade = CascadeType.REMOVE)//mappedBy和@JoinColumn、@JoinTable是互斥的！！！
    private List<Comment> comments=new ArrayList<>();//尽管不使用mappedBy放弃关系维护权，但是仍然不能绑定关系！
    @ManyToOne
    @JoinColumn(name = "type_id",referencedColumnName = "id")
    private Type type;
    @ManyToMany
    @JoinTable(name = "t_blog_tag"
            ,joinColumns = @JoinColumn(name = "blog_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "id"))
    private List<Tag> tags=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Transient
    private String tagIds;

    public Blog(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public boolean isRecommend_toggle() {
        return recommend_toggle;
    }

    public void setRecommend_toggle(boolean recommend_toggle) {
        this.recommend_toggle = recommend_toggle;
    }

    public boolean isShare_toggle() {
        return share_toggle;
    }

    public void setShare_toggle(boolean share_toggle) {
        this.share_toggle = share_toggle;
    }

    public boolean isAppreciate_toggle() {
        return appreciate_toggle;
    }

    public void setAppreciate_toggle(boolean appreciate_toggle) {
        this.appreciate_toggle = appreciate_toggle;
    }

    public boolean isComment_toggle() {
        return comment_toggle;
    }

    public void setComment_toggle(boolean comment_toggle) {
        this.comment_toggle = comment_toggle;
    }

    public String getPicture_addr() {
        return picture_addr;
    }

    public void setPicture_addr(String picture_addr) {
        this.picture_addr = picture_addr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getView_times() {
        return view_times;
    }

    public void setView_times(int view_times) {
        this.view_times = view_times;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isPublish_toggle() {
        return publish_toggle;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setPublish_toggle(boolean publish_toggle) {
        this.publish_toggle = publish_toggle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", original=" + original +
                ", recommend_toggle=" + recommend_toggle +
                ", share_toggle=" + share_toggle +
                ", appreciate_toggle=" + appreciate_toggle +
                ", comment_toggle=" + comment_toggle +
                ", publish_toggle=" + publish_toggle +
                ", picture_addr='" + picture_addr + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", view_times=" + view_times +
//                ", comments=" + comments +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }
}
