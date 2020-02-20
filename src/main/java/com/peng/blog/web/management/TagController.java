package com.peng.blog.web.management;

import com.peng.blog.entity.Tag;
import com.peng.blog.exception.NotFoundException;
import com.peng.blog.service.TagService;
import com.peng.blog.utils.Message;
import com.peng.blog.utils.PageDefinition;
import com.peng.blog.utils.StatusCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
* @Author:         PENG
* @CreateDate:     2020/2/12
*/
@Controller
@RequestMapping("/manage/tag")
public class TagController {

    private TagService tagService;

    public TagController() {
    }

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = {"","/list"})
    public String list(@RequestParam(value = "page",defaultValue = "0")int pageNum, Model model){
        Pageable pageable=new PageRequest(pageNum,PageDefinition.TYPE_PAGE_SIZE);
        model.addAttribute("page",tagService.listTagByPage(pageable));
        return "management/manageTag";
    }

    @PostMapping("/add")
    public String add(Tag tag, RedirectAttributes redirectAttributes){
        Message message=new Message();
        if(tagService.saveTag(tag)==null){
            message.setCode(StatusCodes.SAVE_FAIL);
            message.setMsg("保存失败，可能原因：\"分类名称已存在\"");
        }else{
            message.setCode(StatusCodes.SAVE_SUCCESS);
            message.setMsg("\""+tag.getName()+"\"保存成功");
        }
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/manage/tag/publish";
    }

    @GetMapping("/edit")
    public String edit(int id,RedirectAttributes redirectAttributes) throws NotFoundException {
        Tag tag=tagService.findOne(id);
        if(tag==null) throw new NotFoundException("不存在id为"+id+"的标签");
        redirectAttributes.addFlashAttribute("tag",tag);
        return "redirect:/manage/tag/publish";
    }

    @GetMapping("/del")
    public String del(int id,RedirectAttributes redirectAttributes){
        Message message=new Message();
        Tag tag=tagService.delOne(id);
        if(tag!=null){
            message.setCode(StatusCodes.DEL_SUCCESS);
            message.setMsg("\""+tag.getName()+"\"删除成功");
        }else{
            message.setCode(StatusCodes.DEL_FAIL);
            message.setMsg("删除失败");
        }
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/manage/tag";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = PageDefinition.TYPE_PAGE_SIZE) Pageable pageable, String name, Model model){
        model.addAttribute("page",tagService.searchByPage(name,pageable));
        model.addAttribute("searching",true);
        return "management/manageTag :: table";
    }
}
