package com.jobsearch.recommendation.infrastructure.source;

import com.jobsearch.recommendation.application.port.JobProvider;
import com.jobsearch.recommendation.domain.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class ActiveJobProvider implements JobProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveJobProvider.class);

    private final AdzunaJobProvider adzunaJobProvider;
    private final MockJobProvider mockJobProvider;

    public ActiveJobProvider(AdzunaJobProvider adzunaJobProvider) {
        this.adzunaJobProvider = adzunaJobProvider;
        this.mockJobProvider = new MockJobProvider();
    }

    @Override
    public List<Job> fetchJobs() {
        List<Job> liveJobs = adzunaJobProvider.fetchJobs();
        if (!liveJobs.isEmpty()) {
            LOGGER.info("Using live jobs from Adzuna provider. Count={}", liveJobs.size());
            return liveJobs;
        }

        LOGGER.info("Falling back to mock job provider.");
        return mockJobProvider.fetchJobs();
    }
}
