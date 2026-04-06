package com.jobsearch.recommendation.application.service;

import com.jobsearch.recommendation.application.port.MatchingService;
import com.jobsearch.recommendation.domain.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchingServiceImpl implements MatchingService {

    private static final double TITLE_BONUS = 10.0;
    private static final double MAX_SCORE = 100.0;

    @Override
    public List<Job> calculateMatchScores(List<Job> jobs, List<String> userSkills) {
        if (jobs == null || jobs.isEmpty()) {
            return List.of();
        }

        Set<String> normalizedUserSkills = normalizeSkills(userSkills);

        for (Job job : jobs) {
            double score = calculateScore(job, normalizedUserSkills);
            job.setMatchScore(Math.min(score, MAX_SCORE));
        }

        return jobs;
    }

    private double calculateScore(Job job, Set<String> normalizedUserSkills) {
        List<String> extractedSkills = job.getExtractedSkills();
        if (extractedSkills == null || extractedSkills.isEmpty()) {
            return hasRelevantTitle(job.getTitle()) ? TITLE_BONUS : 0.0;
        }

        long matchedSkills = extractedSkills.stream()
                .map(skill -> skill.toLowerCase(Locale.ENGLISH))
                .filter(normalizedUserSkills::contains)
                .count();

        double score = (matchedSkills * 100.0) / extractedSkills.size();
        if (hasRelevantTitle(job.getTitle())) {
            score += TITLE_BONUS;
        }

        return score;
    }

    private Set<String> normalizeSkills(List<String> userSkills) {
        if (userSkills == null) {
            return Set.of();
        }

        return userSkills.stream()
                .filter(skill -> skill != null && !skill.isBlank())
                .map(skill -> skill.toLowerCase(Locale.ENGLISH).trim())
                .collect(Collectors.toSet());
    }

    private boolean hasRelevantTitle(String title) {
        if (title == null || title.isBlank()) {
            return false;
        }

        String normalizedTitle = title.toLowerCase(Locale.ENGLISH);
        return normalizedTitle.contains("java") || normalizedTitle.contains("backend");
    }
}
