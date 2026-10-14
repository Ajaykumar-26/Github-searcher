package com.freightfox.githubsearcher.client;

import com.freightfox.githubsearcher.dto.GithubRepositoryResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GithubApiClient {

    private final RestClient restClient;

    public GithubApiClient(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://api.github.com")
                .build();
    }

    public  GithubRepositoryResponse searchRepositories(
            String query, String language, String sort){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/repositories")
                        .queryParam("q",query + " language:" + language)
                        .queryParam("sort",sort)
                        .build())
                .retrieve()
                .body(GithubRepositoryResponse.class);
    }
}
