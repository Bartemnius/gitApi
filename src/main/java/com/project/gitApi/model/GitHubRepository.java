package com.project.gitApi.model;

import lombok.Data;

@Data
public class GitHubRepository {
    private String name;
    private boolean fork;
}
