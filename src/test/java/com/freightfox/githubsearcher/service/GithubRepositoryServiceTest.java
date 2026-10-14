 package com.freightfox.githubsearcher.service;

import com.freightfox.githubsearcher.client.GithubApiClient;
import com.freightfox.githubsearcher.repository.GithubRepositoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freightfox.githubsearcher.dto.GithubOwnerDto;
import com.freightfox.githubsearcher.dto.GithubRepositoryDto;
import com.freightfox.githubsearcher.dto.GithubRepositoryResponse;
import com.freightfox.githubsearcher.entity.GithubRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class GithubRepositoryServiceTest {

    @Mock
    private GithubApiClient githubApiClient;

    @Mock
    private GithubRepositoryRepository repository;

    @InjectMocks
    private GithubRepositoryService service;

    @Test
    void getRepositoriesShouldReturnResults() {
        when(repository.findAll(
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class
                )
        )).thenReturn(java.util.Collections.emptyList());

        var result = service.getRepositories(null, null, "stars");

        assertNotNull(result);
        verify(repository).findAll(
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class
                )
        );
    }

    @Test
    void getRepositoriesShouldFilterByLanguage() {
        when(repository.findByLanguagesIgnoreCase(
                org.mockito.ArgumentMatchers.eq("Java"),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class)
        )).thenReturn(java.util.Collections.emptyList());

        var result = service.getRepositories("Java", null, "stars");

        assertNotNull(result);

        verify(repository).findByLanguagesIgnoreCase(
                org.mockito.ArgumentMatchers.eq("Java"),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class)
        );
    }

    @Test
    void getRepositoriesShouldFilterByMinimumStars() {
        when(repository.findByStarsGreaterThanEqual(
                org.mockito.ArgumentMatchers.eq(1000L),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class)
        )).thenReturn(java.util.Collections.emptyList());

        var result = service.getRepositories(null, 1000L, "stars");

        assertNotNull(result);

        verify(repository).findByStarsGreaterThanEqual(
                org.mockito.ArgumentMatchers.eq(1000L),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class)
        );
    }

    @Test
    void getRepositoriesShouldFilterByLanguageAndMinimumStars() {
        when(repository.findByLanguagesIgnoreCaseAndStarsGreaterThanEqual(
                org.mockito.ArgumentMatchers.eq("Java"),
                org.mockito.ArgumentMatchers.eq(1000L),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class)
        )).thenReturn(java.util.Collections.emptyList());

        var result = service.getRepositories("Java", 1000L, "stars");

        assertNotNull(result);

        verify(repository).findByLanguagesIgnoreCaseAndStarsGreaterThanEqual(
                org.mockito.ArgumentMatchers.eq("Java"),
                org.mockito.ArgumentMatchers.eq(1000L),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Sort.class)
        );
    }

    @Test
    void getRepositoriesShouldRejectInvalidSort() {
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getRepositories(null, null, "invalid")
        );
    }

    @Test
    void searchAndSaveShouldMapAndSaveRepository() {
        // Arrange: Create a mock GitHub repository response.
        GithubRepositoryDto dto = new GithubRepositoryDto();
        dto.setId(12345L);
        dto.setName("spring-boot-demo");
        dto.setDescription("A Spring Boot demo project");
        dto.setLanguage("Java");
        dto.setStars(1500L);
        dto.setForks(300L);
        dto.setHtmlUrl("https://github.com/example/spring-boot-demo");
        dto.setOpenIssues(10L);
        dto.setCreatedAt("2025-01-01T10:00:00Z");
        dto.setUpdatedAt("2026-01-01T10:00:00Z");

        GithubOwnerDto owner = new GithubOwnerDto();
        owner.setLogin("example");
        dto.setOwner(owner);

        GithubRepositoryResponse response = new GithubRepositoryResponse();
        response.setItems(List.of(dto));

        when(githubApiClient.searchRepositories(
                "spring boot", "Java", "stars"))
                .thenReturn(response);

        when(repository.save(any(GithubRepository.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act: Execute the service method.
        List<GithubRepository> result = service.searchAndSave(
                "spring boot", "Java", "stars");

        // Assert: Check the result and mapped fields.
        assertEquals(1, result.size());
        assertEquals(12345L, result.get(0).getId());
        assertEquals("spring-boot-demo", result.get(0).getName());
        assertEquals("Java", result.get(0).getLanguages());
        assertEquals("example", result.get(0).getOwner());
        assertEquals(1500L, result.get(0).getStars());
        assertEquals(300L, result.get(0).getForks());

        assertEquals(
                java.time.Instant.parse("2025-01-01T10:00:00Z"),
                result.get(0).getCreatedAt());

        verify(githubApiClient).searchRepositories(
                "spring boot", "Java", "stars");

        verify(repository, times(1)).save(any(GithubRepository.class));
    }

    @Test
    void searchAndSaveShouldReturnEmptyListWhenResponseIsNull() {
        when(githubApiClient.searchRepositories(
                "unknown project", "Java", "stars"))
                .thenReturn(null);

        var result = service.searchAndSave(
                "unknown project", "Java", "stars");

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(repository, times(0)).save(any(GithubRepository.class));
    }

    @Test
    void searchAndSaveShouldReturnEmptyListWhenItemsAreNull() {
        GithubRepositoryResponse response = new GithubRepositoryResponse();
        response.setItems(null);

        when(githubApiClient.searchRepositories(
                "unknown project", "Java", "stars"))
                .thenReturn(response);

        var result = service.searchAndSave(
                "unknown project", "Java", "stars");

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(repository, times(0)).save(any(GithubRepository.class));
    }

    @Test
    void searchAndSaveShouldSaveRepositoryUsingGitHubId() {
        GithubRepositoryDto dto = new GithubRepositoryDto();
        dto.setId(12345L);
        dto.setName("spring-boot-demo");
        dto.setLanguage("Java");
        dto.setStars(2000L);
        dto.setForks(400L);

        GithubRepositoryResponse response = new GithubRepositoryResponse();
        response.setItems(List.of(dto));

        when(githubApiClient.searchRepositories(
                "spring boot", "Java", "stars"))
                .thenReturn(response);

        when(repository.save(any(GithubRepository.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.searchAndSave(
                "spring boot", "Java", "stars");

        assertEquals(1, result.size());
        assertEquals(12345L, result.get(0).getId());

        verify(repository).save(
                org.mockito.ArgumentMatchers.argThat(
                        entity -> entity.getId().equals(12345L)
                )
        );
    }
    
    @Test
    void searchAndSaveShouldUpdateExistingRepository() {
        // Arrange: Existing repository in the database.
        GithubRepository existing = new GithubRepository();
        existing.setId(12345L);
        existing.setName("old-name");
        existing.setStars(100L);

        // New data returned by GitHub.
        GithubRepositoryDto dto = new GithubRepositoryDto();
        dto.setId(12345L);
        dto.setName("new-name");
        dto.setLanguage("Java");
        dto.setStars(500L);
        dto.setForks(100L);

        GithubRepositoryResponse response = new GithubRepositoryResponse();
        response.setItems(List.of(dto));

        when(githubApiClient.searchRepositories(
                "spring boot", "Java", "stars"))
                .thenReturn(response);

        when(repository.findById(12345L))
                .thenReturn(java.util.Optional.of(existing));

        when(repository.save(any(GithubRepository.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act.
        var result = service.searchAndSave(
                "spring boot", "Java", "stars");

        // Assert.
        assertEquals(1, result.size());
        assertEquals(12345L, result.get(0).getId());
        assertEquals("new-name", result.get(0).getName());
        assertEquals(500L, result.get(0).getStars());

        verify(repository).findById(12345L);
        verify(repository).save(existing);
    }

}