package com.project.gitApi.controller;


import com.project.gitApi.model.GitHubRepository;
import com.project.gitApi.model.GitHubUser;
import com.project.gitApi.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/users/{user}")
    public GitHubUser getUser(@PathVariable String user) {
        return apiService.getUser(user);
    }

    @GetMapping("/users/{user}/repos")
    public List<GitHubRepository> getReposByUser(@PathVariable String user) {
        return apiService.getRepositories(user);
    }


}
