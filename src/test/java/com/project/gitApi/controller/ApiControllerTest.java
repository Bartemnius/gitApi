package com.project.gitApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gitApi.model.Branch;
import com.project.gitApi.model.GitHubRepository;
import com.project.gitApi.model.GitHubUser;
import com.project.gitApi.model.UserInfo;
import com.project.gitApi.service.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ApiController.class)
class ApiControllerTest {
    @MockBean
    private ApiService apiService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetUser() throws Exception {
        //given
        String userName = "testUser";
        //when
        when(apiService.getUser(userName)).thenReturn(new GitHubUser());
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{user}", userName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetReposByUser() throws Exception {
        //given
        String userName = "testUser";
        List<GitHubRepository> repositories = List.of(new GitHubRepository(),
                new GitHubRepository());
        //when
        when(apiService.getRepositories(userName)).thenReturn(repositories);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{user}/repos", userName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(repositories.size()));
    }

    @Test
    public void testGetBranchesForRepo() throws Exception {
        //given
        String userName = "testUser";
        String repoName = "testRepo";
        List<Branch> branches = List.of(new Branch(), new Branch());
        when(apiService.getBranches(userName, repoName)).thenReturn(branches);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{user}/{repo}/branches", userName, repoName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(branches.size()));
    }

    @Test
    public void testGetUserInfo() throws Exception {
        //given
        String userName = "testUser";
        UserInfo userInfo = new UserInfo(userName, null);
        when(apiService.getUserInfo(userName)).thenReturn(userInfo);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getInfo/{user}", userName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value(userName));
    }
}