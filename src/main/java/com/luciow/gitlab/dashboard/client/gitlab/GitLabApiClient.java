package com.luciow.gitlab.dashboard.client.gitlab;

import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import com.luciow.gitlab.dashboard.client.gitlab.model.Pipeline;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "gitlab", url = "${gitlab.url}")
public interface GitLabApiClient {

    @RequestMapping(value = "/api/v3/projects/{projectId}/pipelines", method = RequestMethod.GET)
    List<Pipeline> getPipelines(@PathVariable("projectId") int projectId, @RequestParam("page") int page, @RequestParam("per_page") int size);

    @RequestMapping(value = "/api/v3/projects/{projectId}/repository/commits/{sha}/builds", method = RequestMethod.GET)
    List<Build> getBuilds(@PathVariable("projectId") int projectId, @PathVariable("sha") String sha, @RequestParam("page") int page, @RequestParam("per_page") int size);

}
