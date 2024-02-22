package com.project.gitApi.service;

import com.project.gitApi.model.Branch;
import com.project.gitApi.model.GitHubRepository;
import com.project.gitApi.model.GitHubUser;
import com.project.gitApi.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<GitHubRepository> getRepositories(String user) {
        String url = GIT_API_ADDRESS + user + "/repos";

        ResponseEntity<List<GitHubRepository>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GitHubRepository>>() {}
        );

        List<GitHubRepository> gitHubRepositoryList = responseEntity.getBody();
        return  gitHubRepositoryList;
    }


    public List<Branch> getBranches(String user, String repo) {
        String url = "https://api.github.com/repos/" + user + "/" + repo + "/branches";
        ResponseEntity<List<Branch>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Branch>>() {}
        );

        List<Branch> branches = responseEntity.getBody();
        return branches;
    }

    public UserInfo getUserInfo(String user) {
        GitHubUser gitHubUser = getUser(user);
        List<GitHubRepository> repositories = getRepositories(user);
        Map<String, List<Branch>> branchesMap = new HashMap<>();
        for (GitHubRepository repo : repositories) {
            List<Branch> branches = getBranches(user, repo.getName());
            branchesMap.put(repo.getName(), branches);
        }
        return new UserInfo(gitHubUser.getLogin(), branchesMap);
    }
}
