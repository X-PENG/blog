package com.peng.blog.web.management;

import com.peng.blog.entity.Type;
import com.peng.blog.exception.NotFoundException;
import com.peng.blog.service.TypeService;
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
@RequestMapping("/manage/type")
public class TypeController {
    private TypeService typeService;

    public TypeController() {
    }

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PostMapping("/add")
    public String add(Type type, RedirectAttributes redirectAttributes){
        Message message=new Message();
        if(typeService.saveType(type)==null){//保存失败
            message.setCode(StatusCodes.SAVE_FAIL);
            message.setMsg("保存失败，可能原因：\"分类名称已存在\"");
        }else{
            message.setCode(StatusCodes.SAVE_SUCCESS);
            message.setMsg("\""+type.getName()+"\"保存成功");
        }
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/manage/type/publish";//防止重复提交表单
    }
    @GetMapping(value = {"/list",""})
    public String list(@RequestParam(value = "page",defaultValue = "0") int pageNum, Model model){
        Pageable pageable=new PageRequest(pageNum, PageDefinition.TYPE_PAGE_SIZE);
        model.addAttribute("page",typeService.listTypeByPage(pageable));
        return "management/manageType";//直接作为视图名解析视图，不是请求转发到其他handler。
    }
    @GetMapping("/edit")
    public String edit(int id,RedirectAttributes redirectAttributes) throws NotFoundException {
        Type type=typeService.findOne(id);
        if(type==null) throw new NotFoundException("不存在id为"+id+"的分类");
        redirectAttributes.addFlashAttribute("type",type);
        return "redirect:/manage/type/publish";//直接作为视图名解析视图，不是请求转发到其他handler。
    }
    @GetMapping("/del")
    public String del(int id,RedirectAttributes redirectAttributes){
        Message message=new Message();
        Type type=typeService.delOne(id);
        if(type!=null){
            message.setCode(StatusCodes.DEL_SUCCESS);
            message.setMsg("\""+type.getName()+"\"删除成功");
        }else{
            message.setCode(StatusCodes.DEL_FAIL);
            message.setMsg("删除失败");
        }
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/manage/type";//直接作为视图名解析视图，不是请求转发到其他handler。
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = PageDefinition.TYPE_PAGE_SIZE) Pageable pageable, String name, Model model){
        model.addAttribute("page",typeService.searchByPage(name,pageable));
        model.addAttribute("searching",true);
        return "management/manageType :: table";//thymeleaf模板引擎可以返回视图片段，即一段html文本，而不是整个html文档。
    }
}
