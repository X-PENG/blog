package com.peng.blog.web.client;

import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Comment;
import com.peng.blog.exception.NotFoundException;
import com.peng.blog.service.BlogService;
import com.peng.blog.service.CommentService;
import com.peng.blog.service.TagService;
import com.peng.blog.service.TypeService;
import com.peng.blog.utils.Message;
import com.peng.blog.utils.PageDefinition;
import com.peng.blog.utils.StatusCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    private TypeService typeService;
    private TagService tagService;
    private BlogService blogService;
    private CommentService commentService;

    public BlogController() {
    }

    @Autowired
    public BlogController(TypeService typeService, TagService tagService, BlogService blogService, CommentService commentService) {
        this.typeService = typeService;
        this.tagService = tagService;
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @GetMapping(value = {"","/index.html"})
    public String index(@PageableDefault(size = PageDefinition.INDEX_BLOG_PAGE_SIZE,sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable
                            , Model model){
        model.addAttribute("page",blogService.listAllPublishedBlogByPage(pageable));
        model.addAttribute("typesTop",typeService.listForTop());
        model.addAttribute("tagsTop",tagService.listForTop());
        model.addAttribute("blogsTop",blogService.listForTop());

        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = PageDefinition.INDEX_BLOG_PAGE_SIZE,sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam("query") String query, Model model){
        model.addAttribute("searching",true);
        Page<Blog> page=blogService.globalSearch(query,pageable);
        if (page.getContent().size()==0){
            Message message=new Message();
            message.setCode(StatusCodes.SEARCH_EMPTY);
            message.setMsg("未能查找到\""+query+"\"相关的博客");
            model.addAttribute("message",message);
        }
        model.addAttribute("page",page);
        return "index:: leftContent";
    }

    //searchResult会自动发送异步请求/search，相当于把首页的查询结果“搬过来”！！！
    @GetMapping("/searchPage")
    public String searchPage(String query,Model model){
        model.addAttribute("query",query);
        return "searchResult";
    }

    //查看一篇博客
    @GetMapping("/blog/{id}")
    public String check(@PathVariable("id")int id,Model model) throws NotFoundException {
        Blog blog=blogService.checkOne(id,true);
        model.addAttribute("blog",blog);
        blog.setContent(null);
        return "blog";
    }

    //异步获取一篇博客的所有内容
    @GetMapping("/content/{id}")
    @ResponseBody
    public String content(@PathVariable("id")int id) throws NotFoundException {
        Blog blog=blogService.checkOne(id,false);
        return blog.getContent();
    }

    //异步获取一篇博客的所有评论
    @GetMapping("/comments/{id}")
    @ResponseBody
    public List<Comment> comments(@PathVariable("id")int id) throws NotFoundException {
        return blogService.getAllCommentsOfOneBlog(id);
    }

    //前台回复评论的接口
    //使用@RequestBody注解来接收前台传递的JSON数据。
    @PostMapping("/reply/{parent}")
    @ResponseBody
    public Comment reply(@PathVariable("parent") int parentId,@RequestBody Comment comment){
        return commentService.saveChildComment(parentId,comment);
    }

    //前台博客评论接口
    //使用@RequestBody注解来接收前台传递的JSON数据。
    @PostMapping("/comment/{blog}")
    @ResponseBody
    public Comment commentBlog(@PathVariable("blog") int blogId,@RequestBody Comment comment){
        return commentService.saveFirstComment(blogId,comment);
    }

    //归档的后台接口
    @GetMapping("/archive")
    public String archive(Model model){
        //返回的应该是map<Integer,List<Blog>>
        Map<Integer,List<Blog>> map= blogService.archive();
        int size=0;
        for(Integer temp:map.keySet()){
            if(map.get(temp)==null){
                size=temp;
                map.remove(temp);
                break;
            }
        }
        model.addAttribute("size",size);
        model.addAttribute("map",map);
        return "archive";
    }

}
