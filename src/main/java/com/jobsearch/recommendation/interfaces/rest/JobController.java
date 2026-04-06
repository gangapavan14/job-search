package com.jobsearch.recommendation.interfaces.rest;

import com.jobsearch.recommendation.application.service.JobService;
import com.jobsearch.recommendation.domain.model.Job;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final List<String> DEFAULT_SKILLS = List.of("Java", "Spring Boot", "SQL");

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/top")
    public List<Job> getTopJobs(@RequestParam(required = false) String skills) {
        return jobService.getTopJobs(resolveSkills(skills));
    }

    private List<String> resolveSkills(String skills) {
        if (skills == null || skills.isBlank()) {
            return DEFAULT_SKILLS;
        }

        return Arrays.stream(skills.split(","))
                .map(String::trim)
                .filter(skill -> !skill.isBlank())
                .toList();
    }
}
