package com.luciow.gitlab.dashboard.controller;


import com.luciow.gitlab.dashboard.client.gitlab.model.Project;
import com.luciow.gitlab.dashboard.domain.Dashboard;
import com.luciow.gitlab.dashboard.model.DashboardUpdate;
import com.luciow.gitlab.dashboard.resolver.AccessToken;
import com.luciow.gitlab.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DashboardController {

    private final DashboardService dashboardService;

    @RequestMapping(value = "/api/rogue/dashboards", method = RequestMethod.POST)
    public Dashboard createDashboard(@RequestBody DashboardUpdate dashboardUpdate) {
        return dashboardService.saveDashboard(dashboardUpdate);
    }

    @RequestMapping(value = "/api/rogue/dashboards/projects", method = RequestMethod.GET)
    public List<Project> getSuggestions(@RequestParam("search") String search, @AccessToken String accessToken) {
        return dashboardService.getSuggestions(search, accessToken);
    }

    @RequestMapping(value = "/api/rogue/dashboards/{id}", method = RequestMethod.GET)
    public Dashboard getDashboard(@PathVariable("id") Long id) {
        return dashboardService.getDashboard(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/api/rogue/dashboards", method = RequestMethod.GET)
    public List<Dashboard> getDashboards(@RequestParam(name = "page", defaultValue = "0") int page) {
        return dashboardService.getDashboards(page).getContent();
    }
}
