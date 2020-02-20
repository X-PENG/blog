//package com.peng.blog.BlogAndComment;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "t_comment")
//public class Comment extends EntityID implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    private String content;
//    private String nickname;
//    private String mail;
//    private String head_photo;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date publish_time;
////    配置单向一对多。但是，要子评论能够设置父评论。
//    @ManyToOne
//    @JoinColumn(name = "parent_id",referencedColumnName = "id")
//    private Comment parentComment;
//    @OneToMany(targetEntity = Comment.class,mappedBy = "parentComment")
////    @JoinColumn(name = "parent_id",referencedColumnName = "id")
//    private Set<Comment> childs=new HashSet<>();
////    配置单向一对多。但是，应该让Comment作为关系维护方，否则性能很低。
//    @ManyToOne
//    @JoinColumn(name = "blog_id",referencedColumnName = "id",nullable = false)
//    private Blog blog;
//    public Comment() {
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
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    public String getHead_photo() {
//        return head_photo;
//    }
//
//    public void setHead_photo(String head_photo) {
//        this.head_photo = head_photo;
//    }
//
//    public Date getPublish_time() {
//        return publish_time;
//    }
//
//    public void setPublish_time(Date publish_time) {
//        this.publish_time = publish_time;
//    }
//
//    public Set<Comment> getChilds() {
//        return childs;
//    }
//
//    public void setChilds(Set<Comment> childs) {
//        this.childs = childs;
//    }
//
//    public Blog getBlog() {
//        return blog;
//    }
//
//    public void setBlog(Blog blog) {
//        this.blog = blog;
//    }
//
//    public Comment getParentComment() {
//        return parentComment;
//    }
//
//    public void setParentComment(Comment parentComment) {
//        this.parentComment = parentComment;
//    }
//
//    @Override
//    public String toString() {
//        return "Comment{" +
//                "id='" + id + '\'' +
//                ", content='" + content + '\'' +
//                ", nickname='" + nickname + '\'' +
//                ", mail='" + mail + '\'' +
//                ", head_photo='" + head_photo + '\'' +
//                ", publish_time=" + publish_time +
//                ", childs=" + childs +
//                '}';
//    }
//}
