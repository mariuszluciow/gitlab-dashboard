package com.luciow.gitlab.dashboard.controller;

import com.luciow.gitlab.dashboard.client.gitlab.model.Group;
import com.luciow.gitlab.dashboard.model.Stream;
import com.luciow.gitlab.dashboard.resolver.AccessToken;
import com.luciow.gitlab.dashboard.service.GitLabService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GitlabGroupController {

    private final GitLabService gitLabService;

    @RequestMapping(value = "/api/gitlab/groups", method = RequestMethod.GET)
    public List<Group> getGroups(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "page", defaultValue = "1") int page, @AccessToken String accessToken) {
        if (StringUtils.hasText(search)) {
            return gitLabService.getGroups(search, page, accessToken);
        } else {
            return gitLabService.getGroups(page, accessToken);
        }
    }

    @RequestMapping(value = "/api/gitlab/groups/{id}", method = RequestMethod.GET)
    public List<Stream> getStreams(@PathVariable("id") int groupId, @RequestParam(value = "page", defaultValue = "1") int page, @AccessToken String accessToken) {
        return gitLabService.getStreams(groupId, page, accessToken);
    }

    @RequestMapping(value = "/api/gitlab/projects/{id}", method = RequestMethod.GET)
    public Stream getStream(@PathVariable("id") int projectId, @AccessToken String accessToken) {
        return gitLabService.getStream(projectId, accessToken);
    }

}
