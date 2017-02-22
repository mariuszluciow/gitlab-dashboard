package com.luciow.gitlab;

import com.google.common.cache.CacheBuilder;
import com.luciow.gitlab.dashboard.DashboardConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@EnableCaching
@EnableOAuth2Sso
@EnableFeignClients
@Import(DashboardConfiguration.class)
public class DashboardApplication extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/index.html", "/", "/*bundle.js", "/*bundle.css").permitAll()
                .antMatchers("/fontawesome*", "/glyphicons*", "/favicon.ico").permitAll()
                .antMatchers("/api/user").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // @formatter:on
    }

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        GuavaCache pipelines = new GuavaCache("pipelines", CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build());
        GuavaCache builds = new GuavaCache("builds", CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build());
        GuavaCache groups = new GuavaCache("groups", CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build());
        GuavaCache projects = new GuavaCache("projects", CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build());
        GuavaCache project = new GuavaCache("project", CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build());
        simpleCacheManager.setCaches(Arrays.asList(pipelines, builds, groups, projects, project));
        return simpleCacheManager;
    }
}
