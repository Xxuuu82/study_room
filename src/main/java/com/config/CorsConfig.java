package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有前端域名访问
        config.addAllowedOrigin("*");
        // 允许携带Cookie
        config.setAllowCredentials(true);
        // 允许所有请求方法（GET/POST等）
        config.addAllowedMethod("*");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 对所有路径生效
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}