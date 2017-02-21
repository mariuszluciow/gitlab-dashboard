package com.luciow.gitlab.dashboard.service;

import com.luciow.gitlab.dashboard.client.gitlab.GitLabApiFacade;
import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import com.luciow.gitlab.dashboard.client.gitlab.model.Pipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Single;

@Slf4j
@Service
public class GitLabPipelineService {

    private final int pageSize;
    private final GitLabApiFacade gitLabApiFacade;

    @Autowired
    public GitLabPipelineService(@Value("${gitlab.page.size}") int pageSize, GitLabApiFacade gitLabApiFacade) {
        this.pageSize = pageSize;
        this.gitLabApiFacade = gitLabApiFacade;
    }

    public Observable<Build> getBuilds(int projectId, int pipelineId, String sha, String accessToken) {
        return Observable.range(1, Integer.MAX_VALUE)
                .concatMap(
                        page -> Observable.defer(() -> Observable.just(gitLabApiFacade.getBuilds(projectId, sha, page, pageSize, accessToken)))
                ).takeWhile(page -> !page.isEmpty())
                .concatMap(Observable::from)
                .filter(someBuild -> someBuild.getPipeline().getId() == pipelineId);
    }

    public Single<Build> getLatestBuild(int projectId, String ref, String buildName, String stage, String accessToken) {
        Pipeline pipeline = getLatestPipeline(projectId, ref, accessToken).toBlocking().value();

        log.info("Found {} pipeline", pipeline.getId());

        return Observable.range(1, Integer.MAX_VALUE)
                .concatMap(
                        page -> Observable.defer(() -> Observable.just(gitLabApiFacade.getBuilds(projectId, pipeline.getSha(), page, pageSize, accessToken)))
                ).takeWhile(page -> !page.isEmpty())
                .concatMap(Observable::from)
                .filter(someBuild -> someBuild.getName().equals(buildName))
                .filter(someBuild -> someBuild.getPipeline().getId() == pipeline.getId())
                .takeFirst(someBuild -> someBuild.getStage().equals(stage))
                .toSingle();
    }

    private Single<Pipeline> getLatestPipeline(int projectId, String ref, String accessToken) {
        return Observable.range(1, Integer.MAX_VALUE)
                .concatMap(
                        page -> Observable.defer(() -> Observable.just(gitLabApiFacade.getPipelines(projectId, page, pageSize, accessToken)))
                )
                .takeWhile(page -> !page.isEmpty())
                .concatMap(Observable::from)
                .takeFirst(somePipeline -> somePipeline.getRef().equals(ref))
                .toSingle();
    }

}
