package com.jobsearch.recommendation.application.service;

import com.jobsearch.recommendation.application.port.SkillExtractionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SkillExtractionServiceImpl implements SkillExtractionService {

    // Kept centralized so we can later replace this with a smarter extractor.
    private static final List<String> KNOWN_SKILLS = List.of(
            "Java",
            "Spring Boot",
            "SQL",
            "MySQL",
            "Hibernate",
            "REST",
            "Docker",
            "Kafka",
            "AWS"
    );

    @Override
    public List<String> extractSkills(String jobDescription) {
        if (jobDescription == null || jobDescription.isBlank()) {
            return List.of();
        }

        String normalizedDescription = jobDescription.toLowerCase(Locale.ENGLISH);
        List<String> matchedSkills = new ArrayList<>();

        for (String skill : KNOWN_SKILLS) {
            if (normalizedDescription.contains(skill.toLowerCase(Locale.ENGLISH))) {
                matchedSkills.add(skill);
            }
        }

        return matchedSkills;
    }
}
