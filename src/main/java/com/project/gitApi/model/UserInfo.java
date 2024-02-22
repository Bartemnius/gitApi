package com.project.gitApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class UserInfo {
    private String login;
    private Map<String, List<Branch>> branches;
}
