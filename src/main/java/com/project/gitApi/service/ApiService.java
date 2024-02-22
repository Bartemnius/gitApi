package com.project.gitApi.service;

import com.project.gitApi.model.GitHubUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;
    private final String GIT_API_ADDRESS = "https://api.github.com/users/";

    public GitHubUser getUser(String user) {
        System.out.println(GIT_API_ADDRESS + user);
        GitHubUser gitHubUser = restTemplate.getForObject(GIT_API_ADDRESS + user, GitHubUser.class);
        System.out.println(gitHubUser);
        return gitHubUser;
    }
}
