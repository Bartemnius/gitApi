package com.project.gitApi.service;

import com.project.gitApi.client.GitHubApiClient;
import com.project.gitApi.model.Branch;
import com.project.gitApi.model.GitHubRepository;
import com.project.gitApi.model.GitHubUser;
import com.project.gitApi.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    private GitHubApiClient gitHubApiClient;
    @InjectMocks
    private ApiService apiService;

    @Test
    public void testGetUser() {
        // given
        String userName = "testUser";
        GitHubUser expectedUser = new GitHubUser();
        //when
        when(gitHubApiClient.getUser(userName)).thenReturn(expectedUser);
        //then
        GitHubUser actualUser = apiService.getUser(userName);
        //assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetRepositories() {
        // given
        String userName = "testUser";
        List<GitHubRepository> expectedRepos = Arrays.asList(new GitHubRepository(), new GitHubRepository());
        //when
        when(gitHubApiClient.getRepositories(userName)).thenReturn(expectedRepos);
        // then
        List<GitHubRepository> actualRepos = apiService.getRepositories(userName);
        // assert
        assertEquals(expectedRepos, actualRepos);
    }

    @Test
    public void testGetBranches() {
        // gievn
        String userName = "testUser";
        String repoName = "project";
        List<Branch> expectedBranches = Arrays.asList(new Branch(), new Branch());
        //when
        when(gitHubApiClient.getBranches(userName, repoName)).thenReturn(expectedBranches);
        // then
        List<Branch> actualBranches = apiService.getBranches(userName, repoName);
        // assert
        assertEquals(expectedBranches, actualBranches);
    }

    @Test
    public void testGetUserInfo() {
        // given
        String userName = "testUser";
        UserInfo expectedUserInfo = new UserInfo(userName, null);
        //when
        when(gitHubApiClient.getUserInfo(userName)).thenReturn(expectedUserInfo);
        // then
        UserInfo actualUserInfo = apiService.getUserInfo(userName);
        // assert
        assertEquals(expectedUserInfo, actualUserInfo);
    }
}
