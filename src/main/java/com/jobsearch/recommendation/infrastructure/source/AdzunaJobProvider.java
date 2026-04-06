package com.jobsearch.recommendation.infrastructure.source;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobsearch.recommendation.application.port.JobProvider;
import com.jobsearch.recommendation.domain.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class AdzunaJobProvider implements JobProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdzunaJobProvider.class);
    private static final String DEFAULT_KEYWORDS = "Java Spring Boot SQL";

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String country;
    private final String appId;
    private final String appKey;
    private final String location;
    private final int resultsPerPage;

    public AdzunaJobProvider(RestTemplateBuilder restTemplateBuilder,
                             @Value("${app.providers.adzuna.base-url:https://api.adzuna.com/v1/api}") String baseUrl,
                             @Value("${app.providers.adzuna.country:in}") String country,
                             @Value("${app.providers.adzuna.app-id:}") String appId,
                             @Value("${app.providers.adzuna.app-key:}") String appKey,
                             @Value("${app.providers.adzuna.location:India}") String location,
                             @Value("${app.providers.adzuna.results-per-page:20}") int resultsPerPage) {
        this.restTemplate = restTemplateBuilder.build();
        this.baseUrl = baseUrl;
        this.country = country.toLowerCase(Locale.ENGLISH);
        this.appId = appId;
        this.appKey = appKey;
        this.location = location;
        this.resultsPerPage = resultsPerPage;
    }

    @Override
    public List<Job> fetchJobs() {
        if (!isConfigured()) {
            LOGGER.info("Adzuna provider skipped because app credentials are not configured.");
            return List.of();
        }

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment("jobs", country, "search", "1")
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey)
                .queryParam("results_per_page", resultsPerPage)
                .queryParam("what", DEFAULT_KEYWORDS)
                .queryParam("where", location)
                .queryParam("content-type", "application/json")
                .build()
                .toUriString();

        try {
            ResponseEntity<AdzunaSearchResponse> response = restTemplate.getForEntity(url, AdzunaSearchResponse.class);
            AdzunaSearchResponse body = response.getBody();
            if (body == null || body.results == null) {
                return List.of();
            }

            return body.results.stream()
                    .map(this::toJob)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (RestClientException exception) {
            LOGGER.warn("Adzuna job fetch failed: {}", exception.getMessage());
            return List.of();
        }
    }

    public boolean isConfigured() {
        return StringUtils.hasText(appId) && StringUtils.hasText(appKey);
    }

    private Job toJob(AdzunaJobResult result) {
        if (result == null || !StringUtils.hasText(result.id) || !StringUtils.hasText(result.title)) {
            return null;
        }

        return new Job(
                result.id,
                defaultText(result.title, "Unknown Role"),
                result.company != null ? defaultText(result.company.displayName, "Unknown Company") : "Unknown Company",
                defaultText(result.description, ""),
                resolveLocation(result),
                parseCreatedAt(result.created),
                List.of(),
                0.0,
                defaultText(result.redirectUrl, "#"),
                "Adzuna"
        );
    }

    private String resolveLocation(AdzunaJobResult result) {
        if (result.location != null && StringUtils.hasText(result.location.displayName)) {
            return result.location.displayName;
        }
        return location;
    }

    private LocalDateTime parseCreatedAt(String created) {
        if (!StringUtils.hasText(created)) {
            return LocalDateTime.now(ZoneId.systemDefault());
        }

        try {
            return OffsetDateTime.parse(created).toLocalDateTime();
        } catch (Exception exception) {
            return LocalDateTime.now(ZoneId.systemDefault());
        }
    }

    private String defaultText(String value, String fallback) {
        return StringUtils.hasText(value) ? value : fallback;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AdzunaSearchResponse {
        private List<AdzunaJobResult> results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AdzunaJobResult {
        private String id;
        private String title;
        private String description;
        private String created;
        @JsonProperty("redirect_url")
        private String redirectUrl;
        private AdzunaCompany company;
        private AdzunaLocation location;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AdzunaCompany {
        @JsonProperty("display_name")
        private String displayName;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AdzunaLocation {
        @JsonProperty("display_name")
        private String displayName;
    }
}
