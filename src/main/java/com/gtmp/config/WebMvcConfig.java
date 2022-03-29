package com.gtmp.config;

import com.gtmp.interceptor.BoardInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Autowired
//    LoginInterceptor loginInterceptor;

//    @Autowired
//    LoginRequiredInterceptor loginRequiredInterceptor;

//    @Autowired
//    MessageInterceptor messageInterceptor;

    @Autowired
    BoardInterceptor boardInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .excludePathPatterns("/static/css/**", "/static/js/**", "/static/img/**", "/static/**");

//        registry.addInterceptor(loginRequiredInterceptor)
//                .excludePathPatterns("/static/css/**", "/static/js/**", "/static/img/**", "/static/**");

//        registry.addInterceptor(messageInterceptor)
//                .excludePathPatterns("/static/css/**", "/static/js/**", "/static/img/**", "/static/**");

        registry.addInterceptor(boardInterceptor)
                .excludePathPatterns("/static/css/**", "/static/js/**", "/static/img/**", "/static/**");
    }


}
