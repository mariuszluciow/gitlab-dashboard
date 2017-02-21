package com.luciow.gitlab.dashboard.controller;

import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import com.luciow.gitlab.dashboard.client.shield.ShieldClient;
import com.luciow.gitlab.dashboard.resolver.AccessToken;
import com.luciow.gitlab.dashboard.service.GitLabPipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Single;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GitlabPipelineController {

    private final GitLabPipelineService gitLabPipelineService;
    private final ShieldClient shieldClient;

    @RequestMapping(value = "/pipeline/{projectId}/{ref}/{stage}/{buildName}/status", method = RequestMethod.GET)
    public Single<String> getLatestBuildStatus(@PathVariable("projectId") int projectId,
                                               @PathVariable("ref") String ref,
                                               @PathVariable("buildName") String buildName,
                                               @PathVariable("stage") String stage, @AccessToken String accessToken) {
        return gitLabPipelineService.getLatestBuild(projectId, ref, buildName, stage, accessToken)
                .map(Build::getStatus);
    }


    @RequestMapping(value = "/pipeline/{projectId}/{ref}/{stage}/{buildName}/avatar", method = RequestMethod.GET)
    public Single<String> getLatestBuildAvatarUrl(@PathVariable("projectId") int projectId,
                                                  @PathVariable("ref") String ref,
                                                  @PathVariable("buildName") String buildName,
                                                  @PathVariable("stage") String stage, @AccessToken String accessToken) {
        return gitLabPipelineService.getLatestBuild(projectId, ref, buildName, stage, accessToken)
                .map(build -> build.getUser().getAvatar_url());
    }

    @ResponseBody
    @RequestMapping(value = "/pipeline/{projectId}/{ref}/{stage}/{buildName}/avatar.jpg", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatar(@PathVariable("projectId") int projectId,
                            @PathVariable("ref") String ref,
                            @PathVariable("buildName") String buildName,
                            @PathVariable("stage") String stage, @AccessToken String accessToken) {
        String avatarUri = gitLabPipelineService.getLatestBuild(projectId, ref, buildName, stage, accessToken)
                .map(build -> build.getUser().getAvatar_url()).toBlocking().value();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(avatarUri, HttpMethod.GET, entity, byte[].class, "1");
        return response.getBody();

    }


    @RequestMapping(value = "/api/pipeline/{projectId}/{ref}/{stage}/{buildName}/status.svg", produces = "image/svg+xml;charset=utf-8")
    public String getPic(@PathVariable("projectId") int projectId,
                         @PathVariable("ref") String ref,
                         @PathVariable("buildName") String buildName,
                         @PathVariable("stage") String stage, @AccessToken String accessToken) {
        Build build = gitLabPipelineService.getLatestBuild(projectId, ref, buildName, stage, accessToken).toBlocking().value();

        String color = "green";
        if (build.getStatus().equals("skipped")) {
            color = "lightgrey";
        } else if (build.getStatus().equals("running")) {
            color = "blue";
        } else if (build.getStatus().equals("pending")) {
            color = "orange";
        } else if (build.getStatus().equals("failed")) {
            color = "red";
        } else if (build.getStatus().equals("canceled")) {
            color = "lightgrey";
        } else if (build.getStatus().equals("created")) {
            color = "lightgrey";
        }

        return shieldClient.getBadge(buildName, build.getStatus(), color, "curl");
    }
}
