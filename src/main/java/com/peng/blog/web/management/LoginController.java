package com.peng.blog.web.management;

import com.peng.blog.entity.User;
import com.peng.blog.service.UserService;
import com.peng.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
/**
* @Author:         PENG
* @CreateDate:     2020/2/11
*/
@Controller
@RequestMapping("/manage")
public class LoginController {
    private UserService userService;

    public LoginController() {
    }

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, RedirectAttributes redirectAttributes){
        User user=userService.checkUser(username, MD5Utils.encode(password));
        if(user==null){
//            return "management/manageLogin";//不应该转发，否则下次登录时路径就会有问题
            redirectAttributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/manage";
        }
        user.setPassword(null);//不要把密码传过去，很不安全。
        session.setAttribute("user",user);
        return "redirect:/manage/welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/manage";
    }
}
