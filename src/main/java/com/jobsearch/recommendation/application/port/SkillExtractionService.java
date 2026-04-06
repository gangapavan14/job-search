package com.jobsearch.recommendation.application.port;

import java.util.List;

public interface SkillExtractionService {

    List<String> extractSkills(String jobDescription);
}
