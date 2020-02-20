package com.peng.blog.web.client;

import com.peng.blog.entity.Blog;
import com.peng.blog.entity.Tag;
import com.peng.blog.service.BlogService;
import com.peng.blog.service.TagService;
import com.peng.blog.utils.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagClientController {
    private TagService tagService;
    private BlogService blogService;

    public TagClientController() {
    }

    @Autowired
    public TagClientController(TagService tagService, BlogService blogService) {
        this.tagService = tagService;
        this.blogService = blogService;
    }

    @GetMapping("/tags")
    public String showBlogByTag(MyPage page, Model model, @RequestParam(value = "id",required = false) Integer tagId){
        List<Tag> tags=tagService.listTag();
        if(tagId==null){//没传tagId，默认查询第一个。
            tagId=tags.get(0).getId();
        }
        List<Blog> blogs=blogService.listBlogByTagAndPage(tagId,page);
        model.addAttribute("page",page);//在这之前，必须先查询，改变page对象的内容。
        model.addAttribute("blogs",blogs);
        model.addAttribute("tags",tags);
        model.addAttribute("selected",tagId);
        return "tags";
    }
}
