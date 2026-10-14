package com.freightfox.githubsearcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freightfox.githubsearcher.dto.GithubOwnerDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryDto {

    private Long id;
    private String name;
    private String description;

    private GithubOwnerDto owner;


    private String language;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("open_issues_count")
    private Long openIssues;

    @JsonProperty("stargazers_count")
    private Long stars;

    @JsonProperty("forks_count")
    private Long forks;

    public GithubRepositoryDto(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(Long openIssues) {
        this.openIssues = openIssues;
    }

    public Long getStars() {
        return stars;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

    public Long getForks() {
        return forks;
    }

    public void setForks(Long forks) {
        this.forks = forks;
    }

    public GithubOwnerDto getOwner() {
        return owner;
    }
    public void setOwner(GithubOwnerDto owner){
        this.owner = owner;
    }
}
