package com.luciow.gitlab.dashboard.service;

import com.luciow.gitlab.dashboard.client.gitlab.GitLabApiFacade;
import com.luciow.gitlab.dashboard.client.gitlab.model.Project;
import com.luciow.gitlab.dashboard.dao.DashboardDao;
import com.luciow.gitlab.dashboard.domain.Dashboard;
import com.luciow.gitlab.dashboard.model.DashboardUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DashboardService {

    private final static int PAGE_SIZE = 24;

    private final DashboardDao dashboardDao;
    private final GitLabApiFacade gitLabApiFacade;

    public Dashboard saveDashboard(DashboardUpdate dashboardUpdate) {
        Dashboard dashboard = new Dashboard();
        dashboard.setName(dashboardUpdate.getName());
        dashboard.setId(dashboardUpdate.getId());
        dashboard.setProjectIdsCol1(dashboardUpdate.getProjectIdsCol1());
        dashboard.setProjectIdsCol2(dashboardUpdate.getProjectIdsCol2());
        return dashboardDao.save(dashboard);
    }

    public List<Project> getSuggestions(String search, String accessToken) {
        return gitLabApiFacade.getProjects(search, accessToken);
    }

    public Optional<Dashboard> getDashboard(Long id) {
        return Optional.of(dashboardDao.findOne(id));
    }

    public Page<Dashboard> getDashboards(int page) {
        return dashboardDao.findAll(new PageRequest(page, PAGE_SIZE));
    }
}
