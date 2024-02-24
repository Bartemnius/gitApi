package com.project.gitApi.client;

import com.project.gitApi.exception.GitHubUserNotFoundException;
import com.project.gitApi.model.Branch;
import com.project.gitApi.model.GitHubRepository;
import com.project.gitApi.model.GitHubUser;
import com.project.gitApi.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GitHubApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubApiClient gitHubApiClient;

    private final String GITHUB_USERS_API_PREFIX = "https://api.github.com/users/";
    private final String GITHUB_REPOS_API_PREFIX = "https://api.github.com/repos/";


    @Test
    public void testGetUser() {
        //given
        String userName = "testUser";
        GitHubUser expectedUser = new GitHubUser();
        ResponseEntity<GitHubUser> responseEntity = new ResponseEntity<>(expectedUser, HttpStatus.OK);
        //when
        when(restTemplate.exchange(
                eq(GITHUB_USERS_API_PREFIX + userName),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(GitHubUser.class)
        )).thenReturn(responseEntity);
        //then
        GitHubUser actualUser = gitHubApiClient.getUser(userName);
        //assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetUser_UserNotFound() {
        //given
        String userName = "nonExistentUser";
        //when & then
        when(restTemplate.exchange(
                eq(GITHUB_USERS_API_PREFIX + userName),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(GitHubUser.class)
        )).thenThrow(new GitHubUserNotFoundException("User " + userName +
                " does not exist!"));
        // assert
        assertThrows(GitHubUserNotFoundException.class, () -> gitHubApiClient.getUser(userName));
    }

    @Test
    public void testGetRepositories() {
        // given
        String userName = "testUser";
        String url = GITHUB_USERS_API_PREFIX + userName + "/repos";
        List<GitHubRepository> expectedRepositories = new ArrayList<>();
        expectedRepositories.add(new GitHubRepository());
        expectedRepositories.add(new GitHubRepository());
        ResponseEntity<List<GitHubRepository>> responseEntity = new ResponseEntity<>(expectedRepositories, HttpStatus.OK);
        //when
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<List<GitHubRepository>>() {
                })
        )).thenReturn(responseEntity);
        //then
        List<GitHubRepository> actualRepositories = gitHubApiClient.getRepositories(userName);
        //assert
        assertEquals(expectedRepositories, actualRepositories);
    }

    @Test
    public void testGetBranches() {
        //given
        String userName = "testUser";
        String repoName = "testRepo";
        String url = GITHUB_REPOS_API_PREFIX + userName + "/" + repoName + "/branches";
        List<Branch> expectedBranches = new ArrayList<>();
        expectedBranches.add(new Branch());
        expectedBranches.add(new Branch());
        ResponseEntity<List<Branch>> responseEntity = new ResponseEntity<>(expectedBranches, HttpStatus.OK);
        //when
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<List<Branch>>() {
                })
        )).thenReturn(responseEntity);
        //then
        List<Branch> actualBranches = gitHubApiClient.getBranches(userName, repoName);
        //assert
        assertEquals(expectedBranches, actualBranches);
    }

    @Test
    public void testGetUserInfo() {
        // given
        String userName = "testUser";
        GitHubUser gitHubUser = new GitHubUser();
        List<GitHubRepository> repositories = new ArrayList<>();
        GitHubRepository repository = new GitHubRepository();
        repository.setName("testRepo");
        repositories.add(repository);
        List<Branch> branches = new ArrayList<>();
        branches.add(new Branch());
        branches.add(new Branch());
        ResponseEntity<GitHubUser> userResponseEntity = new ResponseEntity<>(gitHubUser, HttpStatus.OK);
        ResponseEntity<List<GitHubRepository>> reposResponseEntity = new ResponseEntity<>(repositories, HttpStatus.OK);
        ResponseEntity<List<Branch>> branchesResponseEntity = new ResponseEntity<>(branches, HttpStatus.OK);
        //when
        when(restTemplate.exchange(
                eq(GITHUB_USERS_API_PREFIX + userName),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(GitHubUser.class)
        )).thenReturn(userResponseEntity);
        when(restTemplate.exchange(
                eq(GITHUB_USERS_API_PREFIX + userName + "/repos"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<List<GitHubRepository>>() {
                })
        )).thenReturn(reposResponseEntity);
        when(restTemplate.exchange(
                eq(GITHUB_REPOS_API_PREFIX + userName + "/" + repository.getName() + "/branches"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<List<Branch>>() {
                })
        )).thenReturn(branchesResponseEntity);
        // then
        UserInfo userInfo = gitHubApiClient.getUserInfo(userName);
        //assert
        assertNotNull(userInfo);
    }
}
