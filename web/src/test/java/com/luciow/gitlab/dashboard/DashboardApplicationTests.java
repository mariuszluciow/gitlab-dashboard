package com.luciow.gitlab.dashboard;

import com.luciow.gitlab.dashboard.client.gitlab.GitLabApiClient;
import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import com.luciow.gitlab.dashboard.client.gitlab.model.Pipeline;
import com.luciow.gitlab.dashboard.client.gitlab.model.PipelineRef;
import com.luciow.gitlab.dashboard.client.shield.ShieldClient;
import com.luciow.gitlab.dashboard.service.GitLabPipelineService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Single;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DashboardApplicationTests.TestConfiguration.class, DashboardConfiguration.class})
public class DashboardApplicationTests {

    @Autowired
    GitLabPipelineService gitLabPipelineService;

    @Autowired
    GitLabApiClient gitLabApiClient;

    @Before
    public void clear() {
        reset(gitLabApiClient);
    }

    @Test
    public void throwsThenNoMatchElement() {
        when(gitLabApiClient.getPipelines(eq(1), anyInt(), anyInt(), anyString())).thenReturn(Collections.emptyList());
        when(gitLabApiClient.getBuilds(eq(1), anyString(), anyInt(), anyInt(), anyString())).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> gitLabPipelineService.getLatestBuild(1, "master", "maven-test", "release", "abs")).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void shouldFindInTheSecondPage() {
        when(gitLabApiClient.getPipelines(eq(1), eq(1), eq(2), anyString())).thenReturn(firstPipelinesPage());
        when(gitLabApiClient.getPipelines(eq(1), eq(2), eq(2), anyString())).thenReturn(secondPipelinesPage());
        when(gitLabApiClient.getBuilds(eq(1), eq("sha123"), eq(1), eq(2), anyString())).thenReturn(firstBuildsPage());
        when(gitLabApiClient.getBuilds(eq(1), eq("sha123"), eq(2), eq(2), anyString())).thenReturn(secondBuildsPage());

        Single<Build> latestBuildStatus = gitLabPipelineService.getLatestBuild(1, "master", "maven-test", "release", "abs");

        assertThat(latestBuildStatus.toBlocking().value()).isNotNull();
    }

    @Test
    public void shouldStopGettingPagesAfterFindingTheMatch() {
        when(gitLabApiClient.getPipelines(eq(1), eq(1), eq(2), anyString())).thenReturn(firstPipelinesPage());
        when(gitLabApiClient.getPipelines(eq(1), eq(2), eq(2), anyString())).thenReturn(secondPipelinesPage());
        when(gitLabApiClient.getBuilds(eq(1), eq("sha123"), eq(1), eq(2), anyString())).thenReturn(secondBuildsPage());

        Single<Build> latestBuildStatus = gitLabPipelineService.getLatestBuild(1, "master", "maven-test", "release", "abs");

        assertThat(latestBuildStatus.toBlocking().value()).isNotNull();
        verify(gitLabApiClient, times(1)).getBuilds(anyInt(), anyString(), anyInt(), anyInt(), anyString());
    }

    private List<Build> firstBuildsPage() {
        Build firstBuild = new Build();
        firstBuild.setName("maven-test");
        firstBuild.setStage("release");
        PipelineRef firstPipelineRef = new PipelineRef();
        firstPipelineRef.setId(2);
        firstBuild.setPipeline(firstPipelineRef);
        Build secondBuild = new Build();
        secondBuild.setName("maven-release");
        secondBuild.setStage("release");
        PipelineRef secondPipelineRef = new PipelineRef();
        secondPipelineRef.setId(1);
        secondBuild.setPipeline(secondPipelineRef);
        return asList(firstBuild, secondBuild);
    }

    private List<Build> secondBuildsPage() {
        Build firstBuild = new Build();
        firstBuild.setName("maven-test");
        firstBuild.setStage("deploy");
        PipelineRef firstPipelineRef = new PipelineRef();
        firstPipelineRef.setId(1);
        firstBuild.setPipeline(firstPipelineRef);
        Build secondBuild = new Build();
        secondBuild.setName("maven-test");
        secondBuild.setStage("release");
        PipelineRef secondPipelineRef = new PipelineRef();
        secondPipelineRef.setId(1);
        secondBuild.setPipeline(secondPipelineRef);
        return asList(firstBuild, secondBuild);
    }

    private List<Pipeline> firstPipelinesPage() {
        Pipeline firstPipeline = new Pipeline();
        firstPipeline.setId(4);
        firstPipeline.setRef("feature-1");
        Pipeline secondPipeline = new Pipeline();
        secondPipeline.setId(3);
        secondPipeline.setRef("feature-2");
        return asList(firstPipeline, secondPipeline);
    }

    private List<Pipeline> secondPipelinesPage() {
        Pipeline firstPipeline = new Pipeline();
        firstPipeline.setId(2);
        firstPipeline.setRef("feature-3");
        Pipeline secondPipeline = new Pipeline();
        secondPipeline.setId(1);
        secondPipeline.setRef("master");
        secondPipeline.setSha("sha123");
        return asList(firstPipeline, secondPipeline);
    }


    @Configuration
    static class TestConfiguration {

        @Bean
        public GitLabApiClient gitLabApiClient() {
            return mock(GitLabApiClient.class);
        }

        @Bean
        public ShieldClient shieldClient() {
            return mock(ShieldClient.class);
        }
    }

}
