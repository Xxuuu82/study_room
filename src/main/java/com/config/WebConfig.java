package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源映射配置
 * 解决/study_room/upload/路径的访问问题
 */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 关键配置：映射/upload/**到static/upload目录
//        // 结合上下文路径后，访问地址为 http://localhost:8080/study_room/upload/文件名.jpg
//        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("classpath:static/upload/") // 项目编译后的静态资源目录
//                .addResourceLocations("file:src/main/resources/static/upload/"); // 源码目录的静态资源（可选，方便开发）
//    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8082") // 改为你的前端地址，不能用 "*"
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true)
                    .allowedHeaders("*")
                    .maxAge(3600);
        }
    }