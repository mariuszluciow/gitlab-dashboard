package com.luciow.gitlab.dashboard.client.gitlab.model;

import lombok.Data;

@Data
public class User {

    private String name;
    private String username;
    private int id;
    private String state;
    private String avatar_url;
    private String web_url;

}
