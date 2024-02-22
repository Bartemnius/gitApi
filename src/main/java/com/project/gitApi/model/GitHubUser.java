package com.project.gitApi.model;

import lombok.Data;

import java.util.List;

@Data
public class GitHubUser {
    private String userName;
    private List<GitHubRepository> gitHubRepositoryList;
}
