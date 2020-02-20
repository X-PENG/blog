package com.peng.blog.web.client;

import com.peng.blog.entity.Type;
import com.peng.blog.service.BlogService;
import com.peng.blog.service.TypeService;
import com.peng.blog.utils.PageDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeClientController {

    private TypeService typeService;
    private BlogService blogService;

    public TypeClientController() {
    }

    @Autowired
    public TypeClientController(TypeService typeService, BlogService blogService) {
        this.typeService = typeService;
        this.blogService = blogService;
    }

    @GetMapping("/types")
    public String showBlogsByType(@PageableDefault(size = PageDefinition.CLIENT_TYPE_PAGE_SIZE,sort = "createTime",direction = Sort.Direction.DESC)Pageable pageable
                            , Model model,@RequestParam(value = "id",required = false) Integer typeId){
        List<Type> types=typeService.listType();
        model.addAttribute("types",types);
        if (typeId==null){
            typeId=types.get(0).getId();//默认用第一个
        }
        model.addAttribute("page",blogService.listBlogByTypeAndPage(typeId,pageable));
        model.addAttribute("selected",typeId);
        return "types";
    }
}
