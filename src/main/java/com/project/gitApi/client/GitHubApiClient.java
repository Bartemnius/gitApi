package com.project.gitApi.client;

import com.project.gitApi.exception.GitHubUserNotFoundException;
import com.project.gitApi.model.Branch;
import com.project.gitApi.model.GitHubRepository;
import com.project.gitApi.model.GitHubUser;
import com.project.gitApi.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GitHubApiClient {

    private final RestTemplate restTemplate;
    private final String GITHUB_USERS_API_PREFIX = "https://api.github.com/users/";
    private final String GITHUB_REPOS_API_PREFIX = "https://api.github.com/repos/";


    public GitHubUser getUser(String user) {
        try {
            ResponseEntity<GitHubUser> gitHubUser =
                    restTemplate.exchange(
                            GITHUB_USERS_API_PREFIX + user,
                            HttpMethod.GET,
                            getEntityWithHeader(),
                            GitHubUser.class);
            return gitHubUser.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new GitHubUserNotFoundException("User " + user + " does not exist!");
        }
    }

    public List<GitHubRepository> getRepositories(String user) {
        String url = GITHUB_USERS_API_PREFIX + user + "/repos";

        ResponseEntity<List<GitHubRepository>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                getEntityWithHeader(),
                new ParameterizedTypeReference<>() {
                }
        );

        return responseEntity.getBody();
    }


    public List<Branch> getBranches(String user, String repo) {
        String url = GITHUB_REPOS_API_PREFIX + user + "/" + repo + "/branches";
        ResponseEntity<List<Branch>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                getEntityWithHeader(),
                new ParameterizedTypeReference<>() {
                }
        );

        return responseEntity.getBody();
    }

    public UserInfo getUserInfo(String user) {
        GitHubUser gitHubUser = getUser(user);
        List<GitHubRepository> repositories = getRepositories(user);
        Map<String, List<Branch>> branchesMap = new HashMap<>();
        for (GitHubRepository repo : repositories) {
            List<Branch> branches = getBranches(user, repo.getName());
            if (!repo.isFork()) branchesMap.put(repo.getName(), branches);
        }
        return new UserInfo(gitHubUser.getLogin(), branchesMap);
    }

    private HttpEntity<String> getEntityWithHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>("headerEntity", headers);
    }
}
