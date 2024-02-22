package com.project.gitApi.model;

import lombok.Data;
import lombok.Getter;

@Data
public class Branch {
    private String name;
    private Commit commit;

    @Getter
    private static class Commit {
        private String sha;
    }
}
