package com.freightfox.githubsearcher.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "github_repositories")
public class GithubRepository {

    @Id
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "owner name")
    private String owner;
    private String languages;
    private Long stars;
    private Long forks;

    @Column(name = "html_url",columnDefinition = "TEXT")
    private String htmlUrl;

    @Column(name = "created_at")
    private Instant createdAt;      //instant is used for storing time stamps.

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Column(name = "open_issues")
    private Long openIssues;

    public GithubRepository(){
        // No arguments constructor
    }

    public Long getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(Long openIssues) {
        this.openIssues = openIssues;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Long getForks() {
        return forks;
    }

    public void setForks(Long forks) {
        this.forks = forks;
    }

    public Long getStars() {
        return stars;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
