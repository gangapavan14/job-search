package com.jobsearch.recommendation.application.port;

import com.jobsearch.recommendation.domain.model.Job;

import java.util.List;

public interface JobProvider {

    List<Job> fetchJobs();
}
