package com.peng.blog.configuration;

import com.peng.blog.properties.UploadFileProperties;
import com.peng.blog.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
* @Author:         PENG
* @CreateDate:     2020/2/10
*/

/**
 * Springboot会整合所有的WebMvcConfigurer实例的配置。
 * 即，容器中所有WebMvcConfigurer都会起作用。
 * 所以，我们自定义WebMvcConfigurer的子类来扩展mvc配置。
 */
//配置类本质上是Component，必须被扫描注册到IOC容器中，其中的配置才会生效！
//这个配置类会被扫描，因为springboot默认扫描范围是：启动类所在包及其子包！
@Configuration//把这个bean注册到IOC容器中
public class WebConfig extends WebMvcConfigurerAdapter {

    private UploadFileProperties uploadFileProperties;

    public WebConfig() {
        super();
    }

    @Autowired
    public WebConfig(UploadFileProperties uploadFileProperties) {
        this.uploadFileProperties = uploadFileProperties;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/about").setViewName("about");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("------------------------------------------------------\n");
        System.out.println("accessPath="+uploadFileProperties.getAccessPath());
        System.out.println("location="+uploadFileProperties.getResourcesLocation());
        System.out.println("------------------------------------------------------\n");
        registry.addResourceHandler(uploadFileProperties.getAccessPath()).addResourceLocations(uploadFileProperties.getResourcesLocation());
    }

    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter01(){
        WebMvcConfigurerAdapter webMvcConfigurerAdapter=new WebMvcConfigurerAdapter() {

            //后台管理视图映射
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/manage").setViewName("management/manageLogin");
                registry.addViewController("/manage/index.html").setViewName("management/manageLogin");
                registry.addViewController("/manage/welcome").setViewName("management/welcome");
                //分类视图
                registry.addViewController("/manage/type/publish").setViewName("management/addType");
                //标签视图
                registry.addViewController("/manage/tag/publish").setViewName("management/addTag");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/manage/**").excludePathPatterns("/manage","/manage/index.html","/manage/login");
            }
        };

        return webMvcConfigurerAdapter;
    }
}
