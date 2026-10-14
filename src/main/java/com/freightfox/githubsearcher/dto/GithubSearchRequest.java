package com.freightfox.githubsearcher.dto;

import jakarta.validation.constraints.NotBlank;

public class GithubSearchRequest {

    @NotBlank(message = "Search query is required")
    private String query;

    @NotBlank(message = "Language is required")
    private String language;

    private String sort = "stars";

    public GithubSearchRequest(){
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

