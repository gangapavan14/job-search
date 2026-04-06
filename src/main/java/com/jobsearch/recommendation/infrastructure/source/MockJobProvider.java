package com.jobsearch.recommendation.infrastructure.source;

import com.jobsearch.recommendation.application.port.JobProvider;
import com.jobsearch.recommendation.domain.model.Job;

import java.time.LocalDateTime;
import java.util.List;

public class MockJobProvider implements JobProvider {

    @Override
    public List<Job> fetchJobs() {
        LocalDateTime now = LocalDateTime.now();

        return List.of(
                buildJob(
                        "job-001",
                        "Java Developer",
                        "Acme Technologies",
                        "Looking for a Java Developer with strong Java, Spring Boot, SQL, REST API, Maven and Git experience.",
                        "Bengaluru, India",
                        now.minusHours(2),
                        "https://www.linkedin.com/jobs/search/?keywords=Java%20Developer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-002",
                        "Backend Engineer",
                        "CloudBridge",
                        "Backend Engineer needed with Java, Spring Boot, Docker, PostgreSQL, microservices and AWS experience.",
                        "Hyderabad, India",
                        now.minusHours(5),
                        "https://www.linkedin.com/jobs/search/?keywords=Backend%20Engineer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-003",
                        "Spring Boot Developer",
                        "FinEdge Solutions",
                        "Hiring Spring Boot Developer with Java, Spring Boot, SQL, Hibernate, JPA and MySQL skills.",
                        "Pune, India",
                        now.minusHours(8),
                        "https://www.naukri.com/spring-boot-developer-jobs-in-india",
                        "Naukri"
                ),
                buildJob(
                        "job-004",
                        "Data Engineer",
                        "DataFlow Labs",
                        "Need Data Engineer with SQL, Python, Spark, Airflow, AWS and ETL pipeline knowledge.",
                        "Chennai, India",
                        now.minusHours(11),
                        "https://www.linkedin.com/jobs/search/?keywords=Data%20Engineer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-005",
                        "Senior Java Developer",
                        "ByteCraft Systems",
                        "Senior Java Developer role requiring Java, Spring Boot, Docker, Kubernetes, SQL and Kafka.",
                        "Mumbai, India",
                        now.minusHours(14),
                        "https://www.naukri.com/java-developer-jobs-in-india",
                        "Naukri"
                ),
                buildJob(
                        "job-006",
                        "Backend Engineer",
                        "NextWave Tech",
                        "Experience in Java, SQL, Redis, Docker, REST API and microservices is preferred.",
                        "Noida, India",
                        now.minusHours(18),
                        "https://www.linkedin.com/jobs/search/?keywords=Backend%20Engineer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-007",
                        "Spring Boot Developer",
                        "ScaleOps",
                        "Hands-on work with Java, Spring Boot, SQL, Maven, JUnit and Docker for backend service development.",
                        "Gurugram, India",
                        now.minusHours(21),
                        "https://www.naukri.com/spring-boot-developer-jobs-in-india",
                        "Naukri"
                ),
                buildJob(
                        "job-008",
                        "Java Developer",
                        "BlueOrbit",
                        "Java Developer opening with Spring, Spring Boot, MySQL, SQL, Git and REST services.",
                        "Kolkata, India",
                        now.minusHours(23),
                        "https://www.linkedin.com/jobs/search/?keywords=Java%20Developer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-009",
                        "Data Engineer",
                        "InsightGrid",
                        "Seeking Data Engineer with SQL, Kafka, Python, Docker and data warehouse experience.",
                        "Bengaluru, India",
                        now.minusHours(26),
                        "https://www.linkedin.com/jobs/search/?keywords=Data%20Engineer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-010",
                        "Backend Engineer",
                        "InfraNova",
                        "Backend Engineer with Java, Spring Boot, PostgreSQL, Docker, AWS and CI/CD pipeline exposure.",
                        "Remote, India",
                        now.minusHours(28),
                        "https://www.naukri.com/backend-engineer-jobs-in-india",
                        "Naukri"
                ),
                buildJob(
                        "job-011",
                        "Spring Boot Developer",
                        "CoreStack Digital",
                        "Build enterprise APIs using Java, Spring Boot, SQL, Oracle, JPA and Hibernate.",
                        "Ahmedabad, India",
                        now.minusHours(3),
                        "https://www.linkedin.com/jobs/search/?keywords=Spring%20Boot%20Developer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-012",
                        "Java Developer",
                        "TechSphere",
                        "Strong Java, SQL, Docker, Linux and Spring Boot skills required for production systems.",
                        "Remote, India",
                        now.minusHours(16),
                        "https://www.naukri.com/java-developer-jobs-in-india",
                        "Naukri"
                ),
                buildJob(
                        "job-013",
                        "Backend Engineer",
                        "Vertex Labs",
                        "Backend role with Java, Spring Boot, MongoDB, Docker and microservices architecture experience.",
                        "Delhi, India",
                        now.minusHours(30),
                        "https://www.linkedin.com/jobs/search/?keywords=Backend%20Engineer&location=India",
                        "LinkedIn"
                ),
                buildJob(
                        "job-014",
                        "Data Engineer",
                        "MetricHive",
                        "Data Engineer position requiring SQL, Python, Airflow, Snowflake and cloud platform knowledge.",
                        "Hyderabad, India",
                        now.minusHours(6),
                        "https://www.naukri.com/data-engineer-jobs-in-india",
                        "Naukri"
                )
        );
    }

    private Job buildJob(String id,
                         String title,
                         String company,
                         String description,
                         String location,
                         LocalDateTime postedAt,
                         String applyLink,
                         String source) {
        return new Job(id, title, company, description, location, postedAt, List.of(), 0.0, applyLink, source);
    }
}
