//package com.peng.blog.BlogAndComment;
//
//import org.hibernate.validator.constraints.NotEmpty;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "t_blog")
//public class Blog extends EntityID implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    @NotEmpty(message = "标题不能为空")
//    @Column(nullable = false, length = 50)
//    private String title;
//    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
////    @Basic(fetch = FetchType.LAZY) // 懒加载
//    @NotEmpty(message = "内容不能为空")
//    @Column(nullable = false)
//    private String content;
//    private Integer original;
//    private boolean recommend_toggle;
//    private boolean share_toggle;
//    private boolean appreciate_toggle;
//    private boolean comment_toggle;
//    private String picture_addr;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date create_time;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date last_update_time;
//    private int view_times;
////    @OneToMany(targetEntity = Comment.class,fetch = FetchType.EAGER)
//    @OneToMany(targetEntity = Comment.class,mappedBy = "blog")//mappedBy和@JoinColumn、@JoinTable是互斥的！！！
//    private Set<Comment> comments=new HashSet<>();//尽管不使用mappedBy放弃关系维护权，但是仍然不能绑定关系！
//
//    public Blog(){
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public Integer getOriginal() {
//        return original;
//    }
//
//    public void setOriginal(Integer original) {
//        this.original = original;
//    }
//
//    public boolean isRecommend_toggle() {
//        return recommend_toggle;
//    }
//
//    public void setRecommend_toggle(boolean recommend_toggle) {
//        this.recommend_toggle = recommend_toggle;
//    }
//
//    public boolean isShare_toggle() {
//        return share_toggle;
//    }
//
//    public void setShare_toggle(boolean share_toggle) {
//        this.share_toggle = share_toggle;
//    }
//
//    public boolean isAppreciate_toggle() {
//        return appreciate_toggle;
//    }
//
//    public void setAppreciate_toggle(boolean appreciate_toggle) {
//        this.appreciate_toggle = appreciate_toggle;
//    }
//
//    public boolean isComment_toggle() {
//        return comment_toggle;
//    }
//
//    public void setComment_toggle(boolean comment_toggle) {
//        this.comment_toggle = comment_toggle;
//    }
//
//    public String getPicture_addr() {
//        return picture_addr;
//    }
//
//    public void setPicture_addr(String picture_addr) {
//        this.picture_addr = picture_addr;
//    }
//
//    public Date getCreate_time() {
//        return create_time;
//    }
//
//    public void setCreate_time(Date create_time) {
//        this.create_time = create_time;
//    }
//
//    public Date getLast_update_time() {
//        return last_update_time;
//    }
//
//    public void setLast_update_time(Date last_update_time) {
//        this.last_update_time = last_update_time;
//    }
//
//    public int getView_times() {
//        return view_times;
//    }
//
//    public void setView_times(int view_times) {
//        this.view_times = view_times;
//    }
//
//    public Set<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(Set<Comment> comments) {
//        this.comments = comments;
//    }
//
//    @Override
//    public String toString() {
////        ", comments=" + comments +
//        return "Blog{" +
//                "id='" + id + '\'' +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", original=" + original +
//                ", recommend_toggle=" + recommend_toggle +
//                ", share_toggle=" + share_toggle +
//                ", appreciate_toggle=" + appreciate_toggle +
//                ", comment_toggle=" + comment_toggle +
//                ", picture_addr='" + picture_addr + '\'' +
//                ", create_time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(create_time) +
//                ", last_update_time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(last_update_time) +
//                ", view_times=" + view_times +
//                '}';
//    }
//}
