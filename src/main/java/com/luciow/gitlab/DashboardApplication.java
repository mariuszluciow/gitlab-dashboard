package com.luciow.gitlab;

import com.google.common.cache.CacheBuilder;
import com.luciow.gitlab.dashboard.DashboardConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@EnableCaching
@EnableFeignClients
@Import(DashboardConfiguration.class)
public class DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        GuavaCache pipelines = new GuavaCache("pipelines", CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build());
        GuavaCache builds = new GuavaCache("builds", CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build());
        simpleCacheManager.setCaches(Arrays.asList(pipelines, builds));
        return simpleCacheManager;
    }
}
