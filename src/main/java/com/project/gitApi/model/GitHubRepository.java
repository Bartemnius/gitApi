package com.project.gitApi.model;

import lombok.Data;

import java.util.List;

@Data
public class GitHubRepository {
    private String name;
    private boolean fork;
}
