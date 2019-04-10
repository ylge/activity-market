package com.geyl.config;

import com.geyl.config.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
　* @description: xss防攻击
　* @author geyl
　* @date 2018-6-13 13:44 
　*/
@Configuration
public class WebConfig {
    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}
