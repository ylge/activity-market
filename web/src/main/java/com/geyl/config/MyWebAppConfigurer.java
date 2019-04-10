package com.geyl.config;

import com.geyl.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author geyl
 * @Title: MyWebAppConfigurer
 * @date 2018-5-28 17:21
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
