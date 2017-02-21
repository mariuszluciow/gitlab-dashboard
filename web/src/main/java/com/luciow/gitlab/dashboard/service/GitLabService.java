package com.luciow.gitlab.dashboard.service;

import com.luciow.gitlab.dashboard.client.gitlab.GitLabApiFacade;
import com.luciow.gitlab.dashboard.client.gitlab.model.Group;
import com.luciow.gitlab.dashboard.client.gitlab.model.Pipeline;
import com.luciow.gitlab.dashboard.client.gitlab.model.Project;
import com.luciow.gitlab.dashboard.model.Stage;
import com.luciow.gitlab.dashboard.model.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class GitLabService {

    private final int pageSize;
    private final GitLabApiFacade gitLabApiFacade;
    private final GitLabPipelineService gitLabPipelineService;

    @Autowired
    public GitLabService(@Value("${gitlab.page.size}") int pageSize, GitLabApiFacade gitLabApiFacade, GitLabPipelineService gitLabPipelineService) {
        this.pageSize = pageSize;
        this.gitLabApiFacade = gitLabApiFacade;
        this.gitLabPipelineService = gitLabPipelineService;
    }

    public List<Group> getGroups(String search, int page, String accessToken) {
        return gitLabApiFacade.getGroups(search, page, pageSize, accessToken);
    }

    public List<Group> getGroups(int page, String accessToken) {
        return gitLabApiFacade.getGroups(page, pageSize, accessToken);
    }

    public List<Stream> getStreams(int groupId, int page, String accessToken) {
        return gitLabApiFacade.getProjects(groupId, page, pageSize, accessToken).parallelStream()
                .map(project -> buildStream(project, accessToken))
                .collect(toList());
    }

    public Stream getStream(int projectId, String accessToken) {
        Project project = gitLabApiFacade.getProject(projectId, accessToken);
        return buildStream(project, accessToken);
    }

    private Stream buildStream(Project project, String accessToken) {
        Stream stream = new Stream();
        stream.setProject(project);

        if (project.isBuilds_enabled()) {
            Optional<Pipeline> firstPipeline = gitLabApiFacade.getPipelines(project.getId(), 1, 1, accessToken).stream().findFirst();
            firstPipeline.ifPresent(pipeline -> stream.setStages(calculateStages(project, pipeline, accessToken)));
        }
        return stream;
    }

    private List<Stage> calculateStages(Project project, Pipeline pipeline, String accessToken) {
        List<Stage> stages = new ArrayList<>();
        gitLabPipelineService.getBuilds(project.getId(), pipeline.getId(), pipeline.getSha(), accessToken)
                .toBlocking()
                .subscribe(build -> {
                    Optional<Stage> firstStage = stages.stream()
                            .filter(stage -> stage.getName().equals(build.getStage()))
                            .findFirst();

                    Stage stage = firstStage.orElseGet(() -> {
                        Stage newStage = new Stage();
                        newStage.setName(build.getStage());
                        return newStage;
                    });
                    stages.remove(stage);
                    stages.add(stage);
                    stage.getBuilds().add(build);
                });
        Collections.reverse(stages);
        return stages;
    }
}
