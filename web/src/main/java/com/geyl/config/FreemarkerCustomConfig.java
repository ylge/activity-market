package com.geyl.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
　* @description:使用shiro标签
　* @author geyl
　* @date 2018-5-18 14:29
　*/
@Configuration
public class FreemarkerCustomConfig {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Bean
    public freemarker.template.Configuration getFreemarkerConfiguration(){
        freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
        configuration.setSharedVariable("shiro",new ShiroTags());
        return configuration;
    }
}
