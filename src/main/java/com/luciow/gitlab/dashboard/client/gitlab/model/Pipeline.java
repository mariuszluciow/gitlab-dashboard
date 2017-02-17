package com.luciow.gitlab.dashboard.client.gitlab.model;


import lombok.Data;

import java.util.Date;

@Data
public class Pipeline {

    private int id;
    private String sha;
    private String ref;
    private String status;
    private String before_sha;
    private boolean tag;
    private Date created_at;
    private Date updated_at;
    private Date started_at;
    private Date finished_at;
    private int duration;
    private User user;

}
