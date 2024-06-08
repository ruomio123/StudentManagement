package com.situ.student2024.config;

import com.situ.student2024.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    /**
     * 向项目中添加拦截器
     *
     * @param registry 拦截器的注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")//指定拦截的路径匹配模式，/**表示拦截一切请求
                .excludePathPatterns("/admin/login/**", "/admin/logout/**", "/admin/captcha/**",
                        "/api/v1/users/login/**", "/**/*.js", "/**/*.css", "/**/*.jpg", "/**/*.png");
    }
}
