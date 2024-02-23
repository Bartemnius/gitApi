package com.project.gitApi.service;

import com.project.gitApi.client.GitHubApiClient;
import com.project.gitApi.model.Branch;
import com.project.gitApi.model.GitHubRepository;
import com.project.gitApi.model.GitHubUser;
import com.project.gitApi.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final GitHubApiClient gitHubApiClient;

    public GitHubUser getUser(String user) {
        return gitHubApiClient.getUser(user);
    }

    public List<GitHubRepository> getRepositories(String user) {
        return gitHubApiClient.getRepositories(user);
    }

    public List<Branch> getBranches(String user, String repo) {
        return gitHubApiClient.getBranches(user, repo);
    }

    public UserInfo getUserInfo(String user) {
        return gitHubApiClient.getUserInfo(user);
    }
}
