package com.luciow.gitlab.dashboard.domain;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class Dashboard {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    private List<Integer> projectIdsCol1;

    @ElementCollection
    private List<Integer> projectIdsCol2;

}
