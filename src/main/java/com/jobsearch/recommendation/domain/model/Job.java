package com.jobsearch.recommendation.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Job {

    private String id;
    private String title;
    private String company;
    private String description;
    private String location;
    private LocalDateTime postedAt;
    private List<String> extractedSkills = new ArrayList<>();
    private double matchScore;
    private String applyLink;
    private String source;

    public Job() {
    }

    public Job(String id, String title, String company, String description, String location, LocalDateTime postedAt,
               List<String> extractedSkills, double matchScore, String applyLink, String source) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.description = description;
        this.location = location;
        this.postedAt = postedAt;
        this.extractedSkills = extractedSkills;
        this.matchScore = matchScore;
        this.applyLink = applyLink;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public List<String> getExtractedSkills() {
        return extractedSkills;
    }

    public void setExtractedSkills(List<String> extractedSkills) {
        this.extractedSkills = extractedSkills;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
