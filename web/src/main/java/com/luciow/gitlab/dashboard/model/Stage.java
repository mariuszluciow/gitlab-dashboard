package com.luciow.gitlab.dashboard.model;

import com.luciow.gitlab.dashboard.client.gitlab.model.Build;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Stage {

    private String name;
    private List<Build> builds = new ArrayList<>();
}
