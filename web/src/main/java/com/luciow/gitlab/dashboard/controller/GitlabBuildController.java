package com.luciow.gitlab.dashboard.controller;

import com.luciow.gitlab.dashboard.client.gitlab.GitLabApiFacade;
import com.luciow.gitlab.dashboard.resolver.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GitlabBuildController {

    private final GitLabApiFacade gitLabApiFacade;

    @RequestMapping(value = "/api/gitlab/projects/{projectId}/builds/{buildId}/retry", method = RequestMethod.POST)
    public void retryBuild(@PathVariable("projectId") int projectId, @PathVariable("buildId") int buildId, @AccessToken String accessToken) {
        gitLabApiFacade.retryBuild(projectId, buildId, accessToken);
    }

    @RequestMapping(value = "/api/gitlab/projects/{projectId}/builds/{buildId}/start", method = RequestMethod.POST)
    public void startBuild(@PathVariable("projectId") int projectId, @PathVariable("buildId") int buildId, @AccessToken String accessToken) {
        gitLabApiFacade.startBuild(projectId, buildId, accessToken);
    }

    @RequestMapping(value = "/api/gitlab/projects/{projectId}/builds/{buildId}/cancel", method = RequestMethod.POST)
    public void cancelBuild(@PathVariable("projectId") int projectId, @PathVariable("buildId") int buildId, @AccessToken String accessToken) {
        gitLabApiFacade.cancelBuild(projectId, buildId, accessToken);
    }


}
