package com.luciow.gitlab.dashboard.client.gitlab.model;

import lombok.Data;

@Data
public class Project {

    private int id;
    private String name;
    private String description;
    private String default_branch;
    private String web_url;
    private boolean builds_enabled;
    private String avatar_url;

}
