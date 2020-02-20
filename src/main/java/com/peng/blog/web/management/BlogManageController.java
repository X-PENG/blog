package com.peng.blog.web.management;

import com.peng.blog.entity.Blog;
import com.peng.blog.entity.User;
import com.peng.blog.enums.BlogCopyrightSource;
import com.peng.blog.service.BlogService;
import com.peng.blog.service.TagService;
import com.peng.blog.service.TypeService;
import com.peng.blog.utils.Message;
import com.peng.blog.utils.PageDefinition;
import com.peng.blog.utils.StatusCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
* @Author:         PENG
* @CreateDate:     2020/2/11
*/
@Controller
@RequestMapping("/manage/blog")
public class BlogManageController {

    private TypeService typeService;
    private BlogService blogService;
    private TagService tagService;

    public BlogManageController() {
    }

    @Autowired
    public BlogManageController(TypeService typeService, BlogService blogService, TagService tagService) {
        this.typeService = typeService;
        this.blogService = blogService;
        this.tagService = tagService;
    }

    @GetMapping(value = {"","/list"})
    public String list(@PageableDefault(size = PageDefinition.MANAGE_BLOG_PAGE_SIZE,sort = {"lastUpdateTime"}
                        ,direction = Sort.Direction.DESC) Pageable pageable, Model model,HttpSession session){
        model.addAttribute("types",typeService.listType());
        User u= (User) session.getAttribute("user");
        model.addAttribute("page",blogService.listByPageAndUser(pageable,u));
        return "management/blogs";
    }

    @RequestMapping("/search")
    public String search(@PageableDefault(size = PageDefinition.MANAGE_BLOG_PAGE_SIZE,sort = {"lastUpdateTime"}
                            ,direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model){
        model.addAttribute("page",blogService.searchByPage(pageable,blog));
        model.addAttribute("searching",true);
//        model.addAttribute("types",typeService.listType());
        return "management/blogs :: table";
    }

    @GetMapping("/publish")
    public String publish(Model model, @ModelAttribute("blog") Blog blog){
        //使用@ModelAttribute("blog") Blog blog：请求中一定有一个blog属性，要么刚new出来的，要么早就携带的！
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("originals", BlogCopyrightSource.values());
        return "management/publishBlog";
    }

    @GetMapping("/edit")
    public String edit(int id,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("blog",blogService.findOne(id));
        return "redirect:/manage/blog/publish";
    }

    //删除做成异步请求
    @GetMapping("/del")
    @ResponseBody
    public Message del(int id,RedirectAttributes redirectAttributes){
        Blog temp=blogService.delOne(id);
        Message message=new Message();
        if(temp==null){
            message.setCode(StatusCodes.DEL_FAIL);
            message.setMsg("删除失败");
        }else{
            message.setCode(StatusCodes.DEL_SUCCESS);
            message.setMsg("标题为\""+temp.getTitle()+"\"的博客删除成功");
        }
        redirectAttributes.addFlashAttribute("message",message);
        return message;
    }

    //更新和添加的逻辑不一样。共用一个请求处理方法，但要判断id是否null以此确定是否是更新或添加。
    @PostMapping("/add")
    public String add(Blog blog, HttpSession session, RedirectAttributes redirectAttributes){
        String status="更新";
        if(blog.getId()==null){//新增博客
            blog.setUser((User)session.getAttribute("user"));
            status="添加";
        }
        Message message=new Message();
        Blog temp=blogService.saveBlog(blog);
        if(temp==null){
            message.setCode(StatusCodes.SAVE_FAIL);
            message.setMsg(status+"失败");
            redirectAttributes.addFlashAttribute("blog",blog);
        }else{
            message.setCode(StatusCodes.SAVE_SUCCESS);
            message.setMsg("标题为\""+blog.getTitle()+"\"的博客"+status+"成功。\n您可以继续编辑修改博客。");
            redirectAttributes.addFlashAttribute("blog", temp);
        }
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/manage/blog/publish";
    }

}
