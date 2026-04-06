package com.jobsearch.recommendation.infrastructure.scheduler;

import com.jobsearch.recommendation.application.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyJobScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyJobScheduler.class);
    private static final List<String> DEFAULT_SKILLS = List.of("Java", "Spring Boot", "SQL");

    private final JobService jobService;

    public DailyJobScheduler(JobService jobService) {
        this.jobService = jobService;
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void refreshJobs() {
        LOGGER.info("Job refresh started");
        jobService.getTopJobs(DEFAULT_SKILLS);
        LOGGER.info("Job refresh completed");
    }
}
