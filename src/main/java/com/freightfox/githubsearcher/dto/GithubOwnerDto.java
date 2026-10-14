package com.freightfox.githubsearcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubOwnerDto {

    private String login;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
