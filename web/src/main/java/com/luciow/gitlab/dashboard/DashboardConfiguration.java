package com.luciow.gitlab.dashboard;

import com.luciow.gitlab.dashboard.resolver.AccessTokenArgumentResolver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Controller
@SpringBootApplication
public class DashboardConfiguration {

    @RequestMapping(value = {"/{path:[^\\.]*}",
            "/{path:[^\\.]*}/{path:[^\\.]*}",
            "/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}",
            "/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}"})
    public String redirect() {
        return "forward:/";
    }

    @Configuration
    public class WebConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new AccessTokenArgumentResolver());
        }

    }
}
