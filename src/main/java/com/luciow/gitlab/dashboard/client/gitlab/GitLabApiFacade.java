package com.luciow.gitlab.dashboard.client.gitlab;


import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import com.luciow.gitlab.dashboard.client.gitlab.model.Pipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GitLabApiFacade {

    private final GitLabApiClient gitLabApiClient;

    @Cacheable("pipelines")
    public List<Pipeline> getPipelines(int projectId, int page, int size) {
        log.info("Getting pipelines for {}, page: {}, size {}", projectId, page, size);
        return gitLabApiClient.getPipelines(projectId, page, size);
    }

    @Cacheable("builds")
    public List<Build> getBuilds(int projectId, String sha, int page, int size) {
        return gitLabApiClient.getBuilds(projectId, sha, page, size);
    }
}
