package com.example.renbook.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/images/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginInterceptor())
                .order(2)
                .addPathPatterns("/**") //모든 요청
                .excludePathPatterns("/", "/search", "/search/**", "/join", "/login", "/logout", "/detail/**", "/css/**", "/images/**", "/*.ico", "/error");

    }
}
