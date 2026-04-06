package com.jobsearch.recommendation.application.service;

import com.jobsearch.recommendation.application.port.JobProvider;
import com.jobsearch.recommendation.application.port.MatchingService;
import com.jobsearch.recommendation.application.port.SkillExtractionService;
import com.jobsearch.recommendation.domain.model.Job;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class JobService {

    private static final double MINIMUM_MATCH_SCORE = 50.0;
    private static final int MAX_RESULTS = 10;
    private static final int RECENT_HOURS = 24;

    private final JobProvider jobProvider;
    private final SkillExtractionService skillExtractionService;
    private final MatchingService matchingService;

    public JobService(JobProvider jobProvider,
                      SkillExtractionService skillExtractionService,
                      MatchingService matchingService) {
        this.jobProvider = jobProvider;
        this.skillExtractionService = skillExtractionService;
        this.matchingService = matchingService;
    }

    public List<Job> getTopJobs(List<String> userSkills) {
        List<Job> jobs = jobProvider.fetchJobs();
        enrichJobsWithExtractedSkills(jobs);

        List<Job> recentJobs = jobs.stream()
                .filter(this::isPostedWithinLast24Hours)
                .toList();

        return matchingService.calculateMatchScores(recentJobs, userSkills).stream()
                .filter(job -> job.getMatchScore() >= MINIMUM_MATCH_SCORE)
                .sorted(Comparator.comparingDouble(Job::getMatchScore).reversed()
                        .thenComparing(Job::getPostedAt, Comparator.reverseOrder()))
                .limit(MAX_RESULTS)
                .toList();
    }

    private void enrichJobsWithExtractedSkills(List<Job> jobs) {
        for (Job job : jobs) {
            job.setExtractedSkills(skillExtractionService.extractSkills(job.getDescription()));
        }
    }

    private boolean isPostedWithinLast24Hours(Job job) {
        return job.getPostedAt() != null
                && job.getPostedAt().isAfter(LocalDateTime.now().minusHours(RECENT_HOURS));
    }
}
