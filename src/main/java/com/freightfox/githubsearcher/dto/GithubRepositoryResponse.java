package com.freightfox.githubsearcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.freightfox.githubsearcher.entity.GithubRepository;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryResponse {

    private int total_count;
    private List<GithubRepositoryDto> items;

    public GithubRepositoryResponse(){
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<GithubRepositoryDto> getItems() {
        return items;
    }

    public void setItems(List<GithubRepositoryDto> items) {
        this.items = items;
    }

    public GithubRepository save(GithubRepository entity) {
        return entity;
    }
}
