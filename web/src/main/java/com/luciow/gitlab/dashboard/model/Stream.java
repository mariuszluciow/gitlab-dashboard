package com.luciow.gitlab.dashboard.model;

import com.luciow.gitlab.dashboard.client.gitlab.model.Project;
import lombok.Data;

import java.util.List;

@Data
public class Stream {
    private Project project;
    private List<Stage> stages;
}
