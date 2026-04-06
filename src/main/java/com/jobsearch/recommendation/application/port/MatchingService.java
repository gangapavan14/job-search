package com.jobsearch.recommendation.application.port;

import com.jobsearch.recommendation.domain.model.Job;

import java.util.List;

public interface MatchingService {

    List<Job> calculateMatchScores(List<Job> jobs, List<String> userSkills);
}
