package com.peng.blog.web.management;

import com.peng.blog.utils.Message;
import com.peng.blog.utils.PhotoNameUtils;
import com.peng.blog.utils.StatusCodes;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
* @Author:         PENG
* @CreateDate:     2020/2/21
 * SpringBoot默认配置了MultipartResolver Bean，不用自己配置。传统spring mvc还要自己配置。
 * 使用StandardServletMultipartResolver上传文件，也是SpringBoot默认使用的上传文件组件。
 * 平常使用的是：CommonsMultipartResolver。
*/
@Controller
public class UploadController {

    @PostMapping("/upload")
    @ResponseBody
    public Message upload(@RequestParam("img")MultipartFile file,HttpServletRequest request) throws IOException {
        Message message=new Message();
        if(!file.getContentType().startsWith("image/")){
            message.setCode(StatusCodes.UPLOAD_FAIL);
            message.setMsg("请上传图片");
            return message;
        }
        //解析文件后缀名
        String originFileName=file.getOriginalFilename();
        String suffix=originFileName.substring(originFileName.indexOf("."));
        //目标文件名
        String destFileName=PhotoNameUtils.GetId()+suffix;
        //上传文件的路径
        String basePath=ResourceUtils.getURL("classpath:static").getPath().substring(1);
        String uploadPath=basePath+ File.separator+"uploadImgs/blogImgs";
        //上传的目标文件
        File targetFile=new File(uploadPath, destFileName);
        //上传到目标文件。本质就是IO，从file的字节输入流取出字节输出到targetFile的字节输出流中。
        if (targetFile.exists()){
            message.setCode(StatusCodes.UPLOAD_FAIL);
            message.setMsg("文件名冲突，请重新上传！");
        }else{
            file.transferTo(targetFile);
            message.setCode(StatusCodes.UPLOAD_SUCCESS);
            String accessUrl=request.getScheme()+"://"+request.getServerName()+":"+
                            request.getServerPort()+request.getContextPath()+"/uploadImgs/blogImgs/"+destFileName;
            message.setMsg("上传成功，请使用如下url引用图片:\n"+accessUrl);
        }
        return message;
    }
}
