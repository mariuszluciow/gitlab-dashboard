package com.luciow.gitlab.dashboard.client.gitlab.model;

import lombok.Data;

import java.util.Date;

@Data
public class Build {

    private int id;
    private String status;
    private String stage;
    private String name;
    private boolean tag;
    private Date started_at;
    private Date created_at;
    private Date finished_at;
    private User user;
    private PipelineRef pipeline;
}
