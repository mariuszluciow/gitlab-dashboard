package com.luciow.gitlab.dashboard.client.gitlab;

import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import com.luciow.gitlab.dashboard.client.gitlab.model.Group;
import com.luciow.gitlab.dashboard.client.gitlab.model.Pipeline;
import com.luciow.gitlab.dashboard.client.gitlab.model.Project;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "gitlab", url = "${gitlab.url}")
public interface GitLabApiClient {

    @RequestMapping(value = "/api/v3/projects/{projectId}/pipelines", method = RequestMethod.GET)
    List<Pipeline> getPipelines(@PathVariable("projectId") int projectId, @RequestParam("page") int page, @RequestParam("per_page") int size, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/projects/{projectId}", method = RequestMethod.GET)
    Project getProject(@PathVariable("projectId") int projectId, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/projects/{projectId}/repository/commits/{sha}/builds", method = RequestMethod.GET)
    List<Build> getBuilds(@PathVariable("projectId") int projectId, @PathVariable("sha") String sha, @RequestParam("page") int page, @RequestParam("per_page") int size, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/groups", method = RequestMethod.GET)
    List<Group> getGroups(@RequestParam("search") String search, @RequestParam("page") int page, @RequestParam("per_page") int size, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/groups", method = RequestMethod.GET)
    List<Group> getGroups(@RequestParam("all_available") boolean allAvailable, @RequestParam("page") int page, @RequestParam("per_page") int size, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/groups/{id}/projects", method = RequestMethod.GET)
    List<Project> getProjects(@PathVariable("id") int groupId, @RequestParam("page") int page, @RequestParam("per_page") int size, @RequestParam("order_by") String orderBy, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/projects", method = RequestMethod.GET)
    List<Project> getProjects(@RequestParam("page") int page, @RequestParam("per_page") int size, @RequestParam("search") String search, @RequestParam("simple") boolean simple, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/projects/{projectId}/builds/{buildId}/play", method = RequestMethod.POST)
    void startBuild(@PathVariable("projectId") int projectId, @PathVariable("buildId") int buildId, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/projects/{projectId}/builds/{buildId}/retry", method = RequestMethod.POST)
    void retryBuild(@PathVariable("projectId") int projectId, @PathVariable("buildId") int buildId, @RequestHeader("Authorization") String authorization);

    @RequestMapping(value = "/api/v3/projects/{projectId}/builds/{buildId}/cancel", method = RequestMethod.POST)
    void cancelBuild(@PathVariable("projectId") int projectId, @PathVariable("buildId") int buildId, @RequestHeader("Authorization") String authorization);
}
