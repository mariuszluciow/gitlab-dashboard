package com.luciow.gitlab.dashboard.client.gitlab;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitLabSecurityInterceptor implements RequestInterceptor {

    @Value("${gitlab.token}")
    String gitLabToken;

    @Override
    public void apply(RequestTemplate template) {
        template.header("PRIVATE-TOKEN", gitLabToken);
    }
}
