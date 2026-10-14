package com.freightfox.githubsearcher.service;

import com.freightfox.githubsearcher.client.GithubApiClient;
import com.freightfox.githubsearcher.dto.GithubRepositoryDto;
import com.freightfox.githubsearcher.dto.GithubRepositoryResponse;
import com.freightfox.githubsearcher.entity.GithubRepository;
import com.freightfox.githubsearcher.repository.GithubRepositoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
public class GithubRepositoryService {

    private final GithubApiClient githubApiClient;
    private final GithubRepositoryRepository repository;

    public GithubRepositoryService(
            GithubApiClient githubApiClient,
            GithubRepositoryRepository repository) {
        this.githubApiClient = githubApiClient;
        this.repository = repository;
    }

    public List<GithubRepository> searchAndSave(
        String query, String language, String sort){
        //1. Fetch repositories from github
        GithubRepositoryResponse response =
                githubApiClient.searchRepositories(query, language, sort);

        if(response == null || response.getItems() == null){
            return new ArrayList<>();
        }
    List<GithubRepository> savedRepositories = new ArrayList<>();

        for(GithubRepositoryDto dto : response.getItems()){
            GithubRepository entity = repository.findById(dto.getId())
                    .orElseGet(GithubRepository::new);

            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setLanguages(dto.getLanguage());
            entity.setStars(dto.getStars());
            entity.setForks(dto.getForks());
            entity.setHtmlUrl(dto.getHtmlUrl());
            entity.setOpenIssues(dto.getOpenIssues());

            // Extract the owner's GitHub username
            if (dto.getOwner()!= null) {
                entity.setOwner(dto.getOwner().getLogin());
            }

            // Convert API timestamps into Instant values
            if (dto.getCreatedAt() != null) {
                entity.setCreatedAt(Instant.parse(dto.getCreatedAt()));
            }

            if (dto.getUpdatedAt() != null) {
                entity.setLastUpdated(Instant.parse(dto.getUpdatedAt()));
            }

            // 4. Save or update the entity in PostgreSQL
            GithubRepository saved = repository.save(entity);
            savedRepositories.add(saved);
        }

        // 5. Return the saved repositories
        return savedRepositories;

    }


    public List<GithubRepository> getRepositories(
            String language, Long minStars, String sortBy) {

        // Default sorting is by stars, highest first.
        String sortField = "stars";

        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "forks":
                    sortField = "forks";
                    break;
                case "updated":
                    sortField = "lastUpdated";
                    break;
                case "stars":
                    sortField = "stars";
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Invalid sort value. Use stars, forks, or updated.");
            }
        }

        Sort sort = Sort.by(
                Sort.Direction.DESC, sortField);

        boolean hasLanguage =
                language != null && !language.isBlank();

        boolean hasMinStars = minStars != null;

        if (hasLanguage && hasMinStars) {
            return repository
                    .findByLanguagesIgnoreCaseAndStarsGreaterThanEqual(
                            language, minStars, sort);
        }

        if (hasLanguage) {
            return repository.findByLanguagesIgnoreCase(language, sort);
        }

        if (hasMinStars) {
            return repository.findByStarsGreaterThanEqual(minStars, sort);
        }

        return repository.findAll(sort);
    }

}


