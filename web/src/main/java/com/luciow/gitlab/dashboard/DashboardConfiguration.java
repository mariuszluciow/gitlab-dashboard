package com.luciow.gitlab.dashboard;

import com.luciow.gitlab.dashboard.resolver.AccessTokenArgumentResolver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@SpringBootApplication
public class DashboardConfiguration {

    @Configuration
    public class WebConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new AccessTokenArgumentResolver());
        }

    }
}
