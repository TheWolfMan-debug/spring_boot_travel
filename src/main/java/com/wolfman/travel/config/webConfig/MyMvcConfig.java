package com.wolfman.travel.config.webConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 所有的WebMvcConfigurerAdapter组件都会一起起作用
     *
     * @return
     */
    @Bean //将组件注册在容器
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //静态资源；  *.css , *.js
                //SpringBoot已经做好了静态资源映射
                registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/**")
                        .excludePathPatterns("/user/findAllRoutes","/user/index","/user/findOne", "/category/findAll", "/user/active", "/checkCode", "/user/registerOk", "/user/login", "/", "/user/register", "/css/**", "/js/**", "/fonts/**", "/img/**", "/images/**");

            }
        };
        return adapter;
    }
}
