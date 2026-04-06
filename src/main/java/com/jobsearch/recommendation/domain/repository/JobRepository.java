package com.jobsearch.recommendation.domain.repository;

import com.jobsearch.recommendation.domain.model.Job;

import java.util.List;

public interface JobRepository {

    void save(Job job);

    void saveAll(List<Job> jobs);

    List<Job> findAll();

    boolean existsById(String id);
}
