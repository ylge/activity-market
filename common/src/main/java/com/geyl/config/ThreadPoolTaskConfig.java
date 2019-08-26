package com.geyl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author geyl
 * @Description: 多线程
 * @date 2019-1-3 17:44
 */
@Configuration
public class ThreadPoolTaskConfig {
    @Bean(name = "ysgThreadPoolTask")
    @Primary
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //线程池维护线程的最少数量
        threadPoolTaskExecutor.setCorePoolSize(5);
        //线程池维护线程的最大数量
        threadPoolTaskExecutor.setMaxPoolSize(20);
        //线程池所使用的缓冲队列
        threadPoolTaskExecutor.setQueueCapacity(64);
        threadPoolTaskExecutor.setThreadNamePrefix("YSG-THREAD");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return threadPoolTaskExecutor;
    }
}
