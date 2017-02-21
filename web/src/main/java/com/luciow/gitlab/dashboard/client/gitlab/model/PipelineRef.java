package com.luciow.gitlab.dashboard.client.gitlab.model;

import lombok.Data;

@Data
public class PipelineRef {
    private int id;
    private String sha;
    private String ref;
    private String status;
}
