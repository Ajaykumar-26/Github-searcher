 package com.freightfox.githubsearcher.controller;

import com.freightfox.githubsearcher.dto.GithubSearchRequest;
import com.freightfox.githubsearcher.entity.GithubRepository;
import com.freightfox.githubsearcher.service.GithubRepositoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
@Validated
public class GithubSearchController {

    private final GithubRepositoryService service;

    public GithubSearchController(GithubRepositoryService service) {
        this.service = service;
    }

    @PostMapping("/search")
    public List<GithubRepository> searchRepositories(
            @Valid @RequestBody GithubSearchRequest request) {

        return service.searchAndSave(
                request.getQuery(),
                request.getLanguage(),
                request.getSort()
        );
    }

    @GetMapping("/repositories")
    public List<GithubRepository> getRepositories(
            @RequestParam(required = false) String language,

            @Min(value = 0, message = "minStars cannot be negative")
            @RequestParam(required = false) Long minStars,

            @RequestParam(defaultValue = "stars") String sort) {

        return service.getRepositories(language, minStars, sort);
    }
}