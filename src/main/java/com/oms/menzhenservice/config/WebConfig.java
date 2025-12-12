package com.oms.menzhenservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**") // 拦截所有接口
                .excludePathPatterns("/api/user/login") // 放行登录
                .excludePathPatterns("/api/user/register") // 放行注册
                .excludePathPatterns("/api/user/verify-user-info") // 放行用户信息验证接口
                .excludePathPatterns("/api/user/reset-password") // 放行密码重置接口
                .excludePathPatterns("/api/dept/list") // 放行科室列表接口
                .excludePathPatterns("/api/dept/**"); // 放行所有科室详情接口
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径
                .allowedOriginPatterns("*") // 允许所有来源 (生产环境建议指定具体域名)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
