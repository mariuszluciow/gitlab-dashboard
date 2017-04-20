package com.luciow.gitlab.dashboard.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class DashboardUpdate {

    @Length(min = 2)
    private String name;
    private Long id;
    private List<Integer> projectIdsCol1;
    private List<Integer> projectIdsCol2;
}
