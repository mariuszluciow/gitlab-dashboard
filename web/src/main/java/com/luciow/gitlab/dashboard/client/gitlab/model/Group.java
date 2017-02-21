package com.luciow.gitlab.dashboard.client.gitlab.model;

import lombok.Data;

@Data
public class Group {

    private int id;
    private String name;
    private String path;
    private String avatar_url;
    private String web_url;
}
