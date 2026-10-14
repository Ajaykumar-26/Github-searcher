package com.freightfox.githubsearcher.repository;

import com.freightfox.githubsearcher.entity.GithubRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubRepositoryRepository
        extends JpaRepository<GithubRepository, Long> {

    List<GithubRepository> findByLanguagesIgnoreCase(
            String languages, Sort sort);

    List<GithubRepository> findByLanguagesIgnoreCaseAndStarsGreaterThanEqual(
            String languages, Long stars, Sort sort);

    List<GithubRepository> findByStarsGreaterThanEqual(
            Long stars, Sort sort);
}
