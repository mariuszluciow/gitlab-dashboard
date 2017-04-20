package com.luciow.gitlab.dashboard.client.gitlab;


import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import com.luciow.gitlab.dashboard.client.gitlab.model.Group;
import com.luciow.gitlab.dashboard.client.gitlab.model.Pipeline;
import com.luciow.gitlab.dashboard.client.gitlab.model.Project;
import feign.FeignException;
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
    public List<Pipeline> getPipelines(int projectId, int page, int size, String accessToken) {
        log.info("Getting pipelines for {}, page: {}, size {}", projectId, page, size);
        return gitLabApiClient.getPipelines(projectId, page, size, "bearer " + accessToken);
    }

    @Cacheable("builds")
    public List<Build> getBuilds(int projectId, String sha, int page, int size, String accessToken) {
        return gitLabApiClient.getBuilds(projectId, sha, page, size, "bearer " + accessToken);
    }

    @Cacheable("groups")
    public List<Group> getGroups(String search, int page, int size, String accessToken) {
        return gitLabApiClient.getGroups(search, page, size, "bearer " + accessToken);
    }

    @Cacheable("groups")
    public List<Group> getGroups(int page, int size, String accessToken) {
        return gitLabApiClient.getGroups(true, page, size, "bearer " + accessToken);
    }

    @Cacheable("projects")
    public List<Project> getProjects(int groupId, int page, int size, String accessToken) {
        return gitLabApiClient.getProjects(groupId, page, size, "last_activity_at", "bearer " + accessToken);
    }

    public List<Project> getProjects(String search, String accessToken) {
        return gitLabApiClient.getProjects(0, 6, search, true, "bearer " + accessToken);
    }

    public void startBuild(int projectId, int buildId, String accessToken) {
        try {
            gitLabApiClient.startBuild(projectId, buildId, "bearer " + accessToken);
        } catch (FeignException ex) {
            if (ex.status() == 403) {
                throw new ForbiddenException(ex);
            } else {
                throw ex;
            }
        }
    }

    public void retryBuild(int projectId, int buildId, String accessToken) {
        try {
            gitLabApiClient.retryBuild(projectId, buildId, "bearer " + accessToken);
        } catch (FeignException ex) {
            if (ex.status() == 403) {
                throw new ForbiddenException(ex);
            } else {
                throw ex;
            }
        }
    }

    public void cancelBuild(int projectId, int buildId, String accessToken) {
        try {
            gitLabApiClient.cancelBuild(projectId, buildId, "bearer " + accessToken);
        } catch (FeignException ex) {
            if (ex.status() == 403) {
                throw new ForbiddenException(ex);
            } else {
                throw ex;
            }
        }
    }

    @Cacheable("project")
    public Project getProject(int projectId, String accessToken) {
        return gitLabApiClient.getProject(projectId, "bearer " + accessToken);
    }
}
